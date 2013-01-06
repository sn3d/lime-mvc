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
	@Inject private InterceptorService interceptorService;

	protected final Collection<Class<?>> controllers;
	protected final Collection<Class<? extends InterceptorHandler>> interceptorHandlerClasses;
	protected Collection<ClassInvoker> classInvokers;
	protected InterceptorChain interceptorChain;
		
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
		this.interceptorService = injector.getInstance(InterceptorService.class);
	}
	

	/**
	 * Constructor for testing purpose
	 * @param controllers
	 * @param injector
	 */
	public MvcDispatcherServlet(Class<?>[] controllers, Injector injector) {
		this( controllers );
		this.injector = injector;
		this.viewResolver = injector.getInstance(ViewResolver.class);
		this.exceptionResolver = injector.getInstance(ExceptionResolver.class);
		this.interceptorService = injector.getInstance(InterceptorService.class);
	}

		
	/**
	 * Constructor used by MvcModule
	 * @param controllerClass
	 */
	public MvcDispatcherServlet(Class<?> controllerClass) {
		this( Arrays.asList( new Class<?>[] { controllerClass } ) );
	}
	
		
	/**
	 * Constructor for testing purpose
	 * @param controllers
	 */	
	public MvcDispatcherServlet(Class<?>[] controllers) {
		this( Arrays.asList( controllers ) );
	}
	

	
	/**
	 * Main constructor used by MvcModule
	 * @param controllers
	 */	
	public MvcDispatcherServlet(List<Class<?>> controllers) {		
		this(controllers, new LinkedList<Class<? extends InterceptorHandler>>());
	}
	
	
	/**
	 * Main constructor used by MvcModule
	 * @param controllers
	 */	
	public MvcDispatcherServlet(List<Class<?>> controllers, List<Class<? extends InterceptorHandler>> interceptorHandlerClasses) {		
		this.controllers = Collections.unmodifiableCollection(controllers);
		this.interceptorHandlerClasses = Collections.unmodifiableCollection(interceptorHandlerClasses);
	}
	
// ------------------------------------------------------------------------

	@Override
	protected final void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		processRequest(req, resp, HttpMethodType.GET);
	}
	

	@Override
	protected final void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		processRequest(req, resp, HttpMethodType.DELETE);
	}


	@Override
	protected final void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		processRequest(req, resp, HttpMethodType.POST);
	}


	@Override
	protected final void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		processRequest(req, resp, HttpMethodType.PUT);
	}
	

	@Override
	public final void init() throws ServletException 
	{
		//interceptor chain init
		InterceptorChain globalChain = interceptorService.getGlobalInterceptorChain();
		this.interceptorChain = globalChain.putInterceptorHandlers(getInterceptorHandlers());
		
		//class invokers initialization
		List<ClassInvoker> allInvokers = new LinkedList<ClassInvoker>();
		ClassScanner scanner = new ClassScanner();
		for (Class<?> controller : controllers) {
			ClassInvoker classInvoker = scanner.scan(controller, injector);
			allInvokers.add( classInvoker );
		}
			
		this.classInvokers = Collections.unmodifiableCollection(allInvokers);		
		
		super.init();
	}

	
	private void processRequest(HttpServletRequest req, HttpServletResponse resp, HttpMethodType reqType)
		throws ServletException, IOException
	{
		Throwable throwedException = null;
		InvokeData data = null;
		
		try {
			if (logger.isLoggable(Level.FINEST)) {
				logger.finest("request '" + req.getRequestURL().toString() +  "' is handled by Lime MVC Servler ");
			}
												
			//prepare invoke data & do preprocessing
			data = 
				new InvokeData(
					req,
					resp,
					null,
					reqType,
					injector );
									
			boolean res = interceptorChain.preHandle(data.getRequest(), data.getResponse());			
			if (!res) {
				return;
			}
				
			//invoke method in controller
			ModelAndView mav = invoke(data);			
			if (mav != null) {
				if (logger.isLoggable(Level.FINEST)) {
					logger.finest("controller produce model " + mav.getModel().toString() );
				}
				
				//postprocessing & resolve view
				interceptorChain.postHandle(data.getRequest(), data.getResponse(), mav);
				viewResolver.resolve(mav.getView(), mav.getModel(), this, req, resp);			
				mav = null;
			}
			
		} catch (Throwable e) {
			throwedException = e;
			exceptionResolver.handleException(e, this, req, resp); 			
		}
		
		interceptorChain.afterCompletion(data.getRequest(), data.getResponse(), throwedException);
	}

	
// ------------------------------------------------------------------------
	
	private List<InterceptorHandler> getInterceptorHandlers() 
	{
		List<InterceptorHandler> handlers = new LinkedList<InterceptorHandler>(); 
		for ( Class<? extends InterceptorHandler> ihc : this.interceptorHandlerClasses) {
			InterceptorHandler handler = this.injector.getInstance(ihc);
			handlers.add(handler);
		}		
		return handlers;
	}
	
				
	protected ModelAndView invoke(InvokeData data) 
	{
		ModelAndView mav = new ModelAndView();				
		int invokedcount = 0;		
		
		for (ClassInvoker invoker : this.classInvokers) {
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
		
		//post-production of model
		for (ClassInvoker classInvoker : this.classInvokers) {
			classInvoker.moveDataToSession(mav.getModel(), data.getRequest());
		}
		mav.getModel().moveObjectsToRequestAttrs(data.getRequest());
		
		return mav;
	}
		
		
		
// ------------------------------------------------------------------------
}