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

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zdevra.guice.mvc.exceptions.NoMethodInvoked;
import org.zdevra.guice.mvc.parameters.ParamProcessorsService;

import com.google.inject.Injector;

/**
 * The Dispatcher servlet dispatch a request to the concrete 
 * controller.
 */
public class MvcDispatcherServlet extends HttpServlet {
	
// ------------------------------------------------------------------------
	
	private static final Logger logger = LoggerFactory.getLogger(MvcDispatcherServlet.class);
	
	@Inject
	private Injector injector;
	
	private final View defaultView;
	private final Class<?> controllerClass;
	private List<MethodInvoker> methodInvokers;
	private List<String> sessionAttributes;
	
// ------------------------------------------------------------------------
	
		
	public MvcDispatcherServlet(Class<?> controllerClass, View defaultView, ConversionService conversionService, ParamProcessorsService paramService) 
	{
		this(controllerClass, defaultView, conversionService, paramService, null);
	}
	
	public MvcDispatcherServlet(Class<?> controllerClass, View defaultView, ConversionService conversionService, ParamProcessorsService paramService, Injector injector) {
		if (injector != null) {
			this.injector = injector;
		}
		
		if (conversionService == null) {
			conversionService = new ConversionService();
		}
				
		if (paramService == null) {
			paramService = new ParamProcessorsService();
		}
		
		this.defaultView = defaultView;
		this.controllerClass = controllerClass;
		
		scanAnotationsOfClass(conversionService, paramService);				
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


	private void processRequest(HttpServletRequest req, HttpServletResponse resp, RequestType reqType)
		throws ServletException, IOException
	{
		try {			
			logger.info("request '" + req.getRequestURL().toString() +  "' is executed by controller '" + this.controllerClass.getName() + "' ");
			
			Object controllerObj = injector.getInstance(controllerClass);								
			ModelAndView mav = invokeMethods(controllerObj, req, resp, reqType);
			
			logger.info("controller produce model " + mav.getModel().toString() );
			
			mav.getModel().moveObjectsToSession(this.sessionAttributes, req.getSession(true));
			mav.getModel().moveObjectsToRequestAttrs(req);
			
			View view = mav.getView();
			if (view != null) {
				logger.info("redirect to view " + view.toString());
				mav.getView().redirectToView(this, req, resp);
			}
			mav = null;

		} catch (Throwable e) {
			ExceptionResolver resolver = injector.getInstance(ExceptionResolver.class);
			if (resolver != null) {
				resolver.handleException(e, req, resp);
			} else { 
				logger.error("Caught exception for the request '" + req.getRequestURL().toString() + ":", e);
			}
		}		
	}

	
// ------------------------------------------------------------------------
		
	private void scanAnotationsOfClass(ConversionService conversionService, ParamProcessorsService paramService) 
	{
		Controller controllerAnotation = controllerClass.getAnnotation(Controller.class);
		if (controllerAnotation == null) {
			throw new IllegalStateException("it's not controller. Missing @Controller annotation.");
		}

		//scan session attributes
		List<String> sessionAttrList = Arrays.asList(controllerAnotation.sessionAttributes());
		this.sessionAttributes = Collections.unmodifiableList(sessionAttrList);
				
		//scan methods
		List<Method> methods = Arrays.asList(this.controllerClass.getMethods());
		List<MethodInvoker> scannedInvokers = new LinkedList<MethodInvoker>();
		for (Method method : methods) {
			//MethodInvoker invoker = ControllerMethodInvoker.createInvoker(method, new MethodParamsBuilder(method, conversionService));			
			MethodInvoker invoker = MethodInvokerImpl.createInvoker(method, paramService, conversionService);
			
			if (invoker != null) {
				scannedInvokers.add(invoker);
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
			
			InvokeData data = new InvokeData(null, req, resp, mav.getModel(), controllerObj, reqType);
			ModelAndView methodMav = invoker.invoke(data);
			
			if (methodMav != null) {
				mav.mergeModelAndView(methodMav);
				methodMav = null;
				invokedcount++;
			}
		}
		
		if (invokedcount == 0) {
			throw new NoMethodInvoked(req);
		}
		
		return mav;
	}
		
// ------------------------------------------------------------------------
}
