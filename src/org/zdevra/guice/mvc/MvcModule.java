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
import org.zdevra.guice.mvc.parameters.ParamProcessorsService;
import org.zdevra.guice.mvc.views.JspView;

import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;

/**
 * MVC module for GUICE. 
 * <p>
 * 
 * First at one read how to use GUICE with web applications. If you are fammiliar 
 * with GUICE servlet, then using of MVC is pretty straight forward.
 * <p>
 * 
 * In your web application, into GuiceServletContextListener implementation,
 * which is instatiating Injector, put new MvcModule with implemented configureControllers() method.
 * <p>
 * 
 * example:
 * <pre><code>
 * public class WebAppConfiguration extends GuiceServletContextListener {
 * ...
 *   protected Injector getInjector() {
 *     Injector injector =  Guice.createInjector(
 *        new MvcModule() {
 *           protected void configureControllers() {
 *             
 *             //exception handlers
 *             
 *              
 *             //controllers
 *              control("/someController/*")
 *                .withController(SomeController.class)
 *                .set();
 *            
 *              control("/anotherController/*")
 *                .withController(AnotherController.class)
 *                .toView(BasicView.create("welcome.jsp"))
 *                .set();
 *
 *              ...
 *           }
 *        }
 *     );
 *     
 *     return injector;
 *   }
 *   
 *   
 * }
 * </code></pre>
 * <p>
 * 
 * MvcModule is extended version of Guice's ServletModule and you can use all ServletModule's
 * methods in configureControllers() method implementation (like serve() etc..).
 *  
 */
public abstract class MvcModule extends ServletModule {
	
// ------------------------------------------------------------------------
		
	private ParamProcessorsService paramService;
	private ConversionService conversionService;
	private ExceptionModuleBuilder exceptionModuleBuilder;
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
		exceptionModuleBuilder = new ExceptionModuleBuilder();
		
		try {
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
						
			//register exception resolver
			ExceptionResolver exceptionResolver = exceptionModuleBuilder.build();
			this.bind(ExceptionResolver.class).toInstance(exceptionResolver);
			
		} finally {
			exceptionModuleBuilder = null;
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
	
	
	protected final ExceptionBindingBuilder exception(Class<? extends Throwable> exceptionClazz) 
	{
		return this.exceptionModuleBuilder.exception(exceptionClazz);
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
	
// ------------------------------------------------------------------------
}
