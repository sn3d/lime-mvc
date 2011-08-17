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
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
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

import com.google.inject.Injector;

/**
 * The Core Dispatcher servlet forward a request to the concrete 
 * controller and after that, the produced model is forwarded into
 * view resolver.
 */
class MvcDispatcherServlet extends HttpServlet {
	
// ------------------------------------------------------------------------
	
	private static final Logger logger = Logger.getLogger(MvcDispatcherServlet.class.getName());
	
	@Inject private Injector injector;	
	@Inject private ViewResolver viewResolver;
	@Inject private ExceptionResolver exceptionResolver;

	private final Collection<Class<?>> controllers;
	private Collection<MethodInvoker> methodInvokers;
	private List<String> sessionAttributes;
	
	
// ------------------------------------------------------------------------
	
	/**
	 * Constructor for testing purpose
	 * @param controllerClass
	 * @param injector
	 */
	public MvcDispatcherServlet(Class<?> controllerClass, Injector injector) {
		this(controllerClass);
		this.injector = injector;
		this.viewResolver = injector.getInstance(ViewResolver.class);
		this.exceptionResolver = injector.getInstance(ExceptionResolver.class);
	}
	

	/**
	 * Constructor for testing purpose
	 * @param controllerClass
	 * @param injector
	 */
	public MvcDispatcherServlet(Class<?>[] controllers, Injector injector) {
		this( controllers );
		this.injector = injector;
		this.viewResolver = injector.getInstance(ViewResolver.class);
		this.exceptionResolver = injector.getInstance(ExceptionResolver.class);
	}

		
	/**
	 * Constructor used by MvcModule
	 * @param controllerClass
	 */
	public MvcDispatcherServlet(Class<?> controllerClass) {
		this( Arrays.asList( new Class<?>[] { controllerClass } ) );
	}
	
	
	/**
	 * Constructor used by MvcModule
	 * @param controllerClass
	 */
	public MvcDispatcherServlet(Class<?>[] controllers) {
		this( Arrays.asList( controllers ) );
	}

	
	/**
	 * Main constructor used by MvcModule
	 * @param controllers
	 */	
	public MvcDispatcherServlet(List<Class<?>> controllers) {
		this.controllers = Collections.unmodifiableCollection(controllers);
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
		try {
			List<MethodInvoker> allInvokers = new LinkedList<MethodInvoker>();
			for (Class<?> controller : controllers) {
				List<MethodInvoker> controllerInvokers = scanAnotationsOfClass(controller);
				allInvokers.addAll(controllerInvokers);
			}
			this.methodInvokers = Collections.unmodifiableCollection(allInvokers);
			
			super.init();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in the servlet's initialization:" + e.getMessage(), e);
			throw new ServletException(e);
		}		
	}

	
	private void processRequest(HttpServletRequest req, HttpServletResponse resp, RequestType reqType)
		throws ServletException, IOException
	{
		try {
			
			if (logger.isLoggable(Level.FINEST)) {
				logger.finest("request '" + req.getRequestURL().toString() +  "' is handled by Lime MVC Servler ");
			}
											
			//prepare invoke data
			Model sessionModel = new Model();
			sessionModel.getObjectsFromSession(this.sessionAttributes, req.getSession(true));
			
			InvokeData data = 
				new InvokeData(
					req,
					resp,
					sessionModel,
					reqType,
					injector );
			
			//invoke method
			ModelAndView mav = invokeMethods(data);			
			if (logger.isLoggable(Level.FINEST)) {
				logger.finest("controller produce model " + mav.getModel().toString() );
			}
			
			//post-production in model
			mav.getModel().moveObjectsToSession(this.sessionAttributes, req.getSession(true));
			mav.getModel().moveObjectsToRequestAttrs(req);
			
			//resolve view
			viewResolver.resolve(mav.getView(), this, req, resp);
			mav = null;

		} catch (Throwable e) {			
			exceptionResolver.handleException(e, this, req, resp); 			
		}		
	}

	
// ------------------------------------------------------------------------
		
	private List<MethodInvoker> scanAnotationsOfClass(Class<?> controllerClass) throws Exception
	{
		Controller controllerAnotation = controllerClass.getAnnotation(Controller.class);
		if (controllerAnotation == null) {
			throw new IllegalStateException("Class is not defined as a controller. Missing @Controller annotation.");
		}

		//scan session attributes & default view
		List<String> sessionAttrList = Arrays.asList(controllerAnotation.sessionAttributes());
		this.sessionAttributes = Collections.unmodifiableList(sessionAttrList);		
				
		//scan methods
		List<Method> methods = Arrays.asList(controllerClass.getMethods());
		List<MethodInvoker> scannedInvokers = new LinkedList<MethodInvoker>();
		for (Method method : methods) {
			
			RequestMapping reqMapping = method.getAnnotation(RequestMapping.class);
			if (reqMapping != null) {
				MethodInvoker invoker = MethodInvokerImpl.createInvoker(controllerClass, method, reqMapping, injector);
				MethodInvoker filteredInvoker = new MethodInvokerFilter(reqMapping, invoker);
				scannedInvokers.add(filteredInvoker);							
			}			
		}
		
		return scannedInvokers; 
	}
	
	
	private ModelAndView invokeMethods(InvokeData data) {
		ModelAndView mav = new ModelAndView();
				
		int invokedcount = 0;		
		for (MethodInvoker invoker : this.methodInvokers) {
			ModelAndView methodMav = invoker.invoke(data);			
			if (methodMav != null) {
				mav.mergeModelAndView(methodMav);
				methodMav = null;
				invokedcount++;
			}
		}
		
		if (invokedcount == 0) {
			throw new NoMethodInvoked(data.getRequest());
		}
		
		return mav;
	}
// ------------------------------------------------------------------------
}
