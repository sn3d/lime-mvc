/*****************************************************************************
 * Copyright 2011 Zdenko Vrabel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 *****************************************************************************/
package org.zdevra.guice.mvc;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zdevra.guice.mvc.exceptions.NoMethodInvoked;
import org.zdevra.guice.mvc.parameters.ParamProcessorsService;
import org.zdevra.guice.mvc.views.NamedView;

import com.google.inject.Injector;

/**
 * The Core Dispatcher servlet forward a request to the concrete 
 * controller and after that, the producet model is forwarded into
 * view resolver.
 */
class MvcDispatcherServlet extends HttpServlet {
	
// ------------------------------------------------------------------------
	
	private static final Logger logger = Logger.getLogger(MvcDispatcherServlet.class.getName());
	
	@Inject
	private Injector injector;
	
	
	private final Class<?> controllerClass;
	private View defaultView;
	private List<MethodInvoker> methodInvokers;
	private List<String> sessionAttributes;
	private ConversionService conversionService;
	private ParamProcessorsService paramService;
	
// ------------------------------------------------------------------------
	
	public MvcDispatcherServlet(Class<?> controllerClass, Injector injector) {
		this(controllerClass, null, null, injector);
	}
		
	public MvcDispatcherServlet(Class<?> controllerClass, ConversionService conversionService, ParamProcessorsService paramService) 
	{
		this(controllerClass, conversionService, paramService, null);
	}
	
	public MvcDispatcherServlet(Class<?> controllerClass, ConversionService aConversionService, ParamProcessorsService aParamService, Injector injector) {
        conversionService = aConversionService;
        paramService = aParamService;

		if (injector != null) {
			this.injector = injector;
		}
		
		if (conversionService == null) {
			conversionService = new ConversionService();
		}
				
		if (paramService == null) {
			paramService = new ParamProcessorsService();
		}
		
		this.controllerClass = controllerClass;				
	}
	
// ------------------------------------------------------------------------

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		processRequest(req, resp, RequestType.GET);
	}
	

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		processRequest(req, resp, RequestType.DELETE);
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		processRequest(req, resp, RequestType.POST);
	}


	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		processRequest(req, resp, RequestType.PUT);
	}
	

	@Override
	public void init() throws ServletException {
		scanAnotationsOfClass(conversionService, paramService);
		super.init();
	}

	
	private void processRequest(HttpServletRequest req, HttpServletResponse resp, RequestType reqType)
		throws ServletException, IOException
	{
		try {						
			if (logger.isLoggable(Level.FINEST)) {
				logger.finest("request '" + req.getRequestURL().toString() +  "' is executed by controller '" + this.controllerClass.getName() + "' ");
			}
						
			Object controllerObj = injector.getInstance(controllerClass);								
			
			ModelAndView mav = invokeMethods(controllerObj, req, resp, reqType);			
			if (logger.isLoggable(Level.FINEST)) {
				logger.finest("controller produce model " + mav.getModel().toString() );
			}
			
			mav.getModel().moveObjectsToSession(this.sessionAttributes, req.getSession(true));
			mav.getModel().moveObjectsToRequestAttrs(req);
			
			ViewResolver resolver = injector.getInstance(ViewResolver.class);
			resolver.resolve(mav.getView(), this, req, resp);

			mav = null;

		} catch (Throwable e) {
			ExceptionResolver resolver = injector.getInstance(ExceptionResolver.class);
			if (resolver != null) {
				resolver.handleException(e, this, req, resp);
			} else { 
				logger.info("Caught exception for the request '" + req.getRequestURL().toString() + ":" + e.toString());				
			}
		}		
	}

	
// ------------------------------------------------------------------------
		
	private void scanAnotationsOfClass(ConversionService conversionService, ParamProcessorsService paramService) 
	{
		Controller controllerAnotation = controllerClass.getAnnotation(Controller.class);
		if (controllerAnotation == null) {
			throw new IllegalStateException("Class is not defined as a controller. Missing @Controller annotation.");
		}

		//scan session attributes & default view
		List<String> sessionAttrList = Arrays.asList(controllerAnotation.sessionAttributes());
		this.sessionAttributes = Collections.unmodifiableList(sessionAttrList);
		this.defaultView = NamedView.create(this.controllerClass);		
				
		//scan methods
		List<Method> methods = Arrays.asList(this.controllerClass.getMethods());
		List<MethodInvoker> scannedInvokers = new LinkedList<MethodInvoker>();
		for (Method method : methods) {
			
			RequestMapping reqMapping = method.getAnnotation(RequestMapping.class);
			if (reqMapping != null) {
				MethodInvoker invoker = MethodInvokerImpl.createInvoker(method, reqMapping, paramService, conversionService);
				MethodInvoker filteredInvoker = new MethodInvokerFilter(reqMapping, invoker);
				scannedInvokers.add(filteredInvoker);							
			}			
		}
		
		this.methodInvokers = Collections.unmodifiableList(scannedInvokers); 
	}
	
	
	private ModelAndView invokeMethods(Object controllerObj, HttpServletRequest req, HttpServletResponse resp, RequestType reqType) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		ModelAndView mav = new ModelAndView(this.defaultView);
		mav.getModel().getObjectsFromSession(this.sessionAttributes, req.getSession(true));
		
		//invoking methods
		int invokedcount = 0;
		for (MethodInvoker invoker : this.methodInvokers) {
			
			InvokeData data = new InvokeData(null, req, resp, mav.getModel(), controllerObj, reqType, injector);
			ModelAndView methodMav = invoker.invoke(data);
			
			if (methodMav != null) {
				mav.mergeModelAndView(methodMav);
				methodMav = null;
				invokedcount++;
			}
		}
		
		if (invokedcount == 0) {
			throw new NoMethodInvoked(req, controllerObj.getClass().getName());
		}
		
		return mav;
	}
	
			
// ------------------------------------------------------------------------
}
