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


import org.zdevra.guice.mvc.ConversionService.ConvertorFactory;
import org.zdevra.guice.mvc.exceptions.ObsoleteException;
import org.zdevra.guice.mvc.parameters.ParamProcessorsService;
import org.zdevra.guice.mvc.views.JspView;

import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;

/**
 * <i>MVC module</i> for GUICE. 
 * <p>
 * 
 * If you are fammiliar with the GUICE servlets, then using of the Lime MVC is pretty straight forward.
 * MvcModule is basically extended version of Guice's ServletModule and you can use all ServletModule's
 * methods in configureControllers() method implementation (like serve() etc..).
 * <p>
 * 
 * In your web application, put new MvcModule with implemented configureControllers() method 
 * into GuiceServletContextListener implementation.
 * 
 * 
 * <p>example:
 * <pre class="prettyprint">
 * public class WebAppConfiguration extends GuiceServletContextListener {
 * ...
 *   protected Injector getInjector() {
 *     Injector injector =  Guice.createInjector(
 *        new MvcModule() {
 *           protected void configureControllers() {
 *              
 *             control("/someController/*")
 *                .withController(SomeController.class)
 *                .set();        
 *              ...
 *           }
 *        }
 *     );
 *     return injector;
 *   }
 * }
 * </pre>
 * Example shows the basic usage and registers the simple controller class. 
 * All requests which stars with '/someController/' will be processed by the SomeController. 
 * 
 * <p>
 *
 * @see Controller 
 * @see Model
 * @see ModelAndView
 * @see View
 * @see ViewResolver
 * @see ExceptionResolver
 *  
 */
public abstract class MvcModule extends ServletModule {
	
// ------------------------------------------------------------------------
		
	private ParamProcessorsService paramService;
	private ConversionService conversionService;
	private ExceptionResolverBuilder exceptionResolverBuilder;
	private ControllerModuleBuilder controllerModuleBuilder;

// ------------------------------------------------------------------------
	
	/**
	 * Put into this method your controllers configuration
	 */
	protected abstract void configureControllers();
	
	
	/**
	 * This method initializate controller servlets
	 */
	@Override
	protected final void configureServlets() 
	{
		if (controllerModuleBuilder != null) {
			throw new IllegalStateException("Re-entry is not allowed.");
		}
		
		paramService = new ParamProcessorsService();
		conversionService = new ConversionService();
		controllerModuleBuilder = new ControllerModuleBuilder();		
		exceptionResolverBuilder = new ExceptionResolverBuilder(binder());
		
		try {
			//default registrations
			bind(ViewResolver.class).to(DefaultViewResolver.class);
			
			bind(ExceptionResolver.class)
				.to(GuiceExceptionResolver.class);
			
			bind(ExceptionHandler.class)
				.annotatedWith(Names.named(ExceptionResolver.DEFAULT_EXCEPTIONHANDLER_NAME))
				.to(DefaultExceptionHandler.class);
			
			configureControllers();
			
			//register MVC controllers
			for (ControllerDefinition def : controllerModuleBuilder.getControllerDefinitions()) {
				String pattern = def.getUrlPattern();				
				MvcDispatcherServlet dispatcher = new MvcDispatcherServlet(
						def.getControllerClass(), 
						def.getDefaultView(),
						conversionService,
						paramService);
				serve(pattern).with(dispatcher);				
			}
						
		} finally {
			exceptionResolverBuilder = null;
			controllerModuleBuilder = null;
			paramService = null;
			conversionService = null;			
		}
	}
		
// ------------------------------------------------------------------------
	
	
	/**
	 * Method bind view instance to view's name. This binding is used by view resolver.
	 */
	protected final void bindView(String viewName, View view) {
		bind(View.class).annotatedWith(Names.named(viewName)).toInstance(view);
	}
	
	
	/**
	 * Method bind view class to view's name. This binding is used by view resolver.
	 */	
	protected final void bindView(String viewName, Class<? extends View> viewClazz) {
		bind(View.class).annotatedWith(Names.named(viewName)).to(viewClazz);
	}	
	
	
	/**
	 * Method bind JSP page to view's name. This binding is used by view resolver.
	 */		
	protected final void bindView(String viewName, String jsp) {
		bind(View.class).annotatedWith(Names.named(viewName)).toInstance(new JspView(jsp));
	}
	
	
	/**
	 * The method registers a custom convertor which converts strings to the
	 * concrete types. These convertors are used for conversions from a HTTP request 
	 * to the method's parameters.
	 * 
	 * The all predefined default convertors are placed in the 'convertors' sub-package.  
	 */
	protected final void registerConvertor(Class<?> type, ConvertorFactory convertorFactory) {
		this.conversionService.registerConvertor(type, convertorFactory);
	}
	
	
	@Deprecated
	protected final ExceptionBindingBuilder exception(Class<? extends Throwable> exceptionClazz) 
	{
		throw new ObsoleteException("bindException()");
	}
	
	
	protected final ExceptionResolverBindingBuilder bindException(Class<? extends Throwable> exceptionClazz) {
		return this.exceptionResolverBuilder.bindException(exceptionClazz);
	}
	
	
	protected final ControllerBindingBuilder control(String urlPattern) 
	{
		return this.controllerModuleBuilder.control(urlPattern);
	}
	
// ------------------------------------------------------------------------	
	
	public static interface ControllerBindingBuilder 
	{
		public ControllerBindingBuilder withController(Class<?> controller);
		public ControllerBindingBuilder toView(View view);
		public ControllerBindingBuilder toView(String viewName);
		public void set();
	}
	
	
	public static interface ExceptionBindingBuilder 
	{
		public void handledBy(ExceptionHandler handler);
		public void toView(View exceptionView);
	}
	
	
	public static interface ExceptionResolverBindingBuilder {
		public void toHandler(Class<? extends ExceptionHandler> handlerClass);
		public void toHandlerInstance(ExceptionHandler handler);
	}
	
// ------------------------------------------------------------------------
}
