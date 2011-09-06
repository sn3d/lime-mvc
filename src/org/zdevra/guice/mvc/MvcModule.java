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


import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;

import org.zdevra.guice.mvc.ConversionService.ConverterFactory;
import org.zdevra.guice.mvc.converters.*;
import org.zdevra.guice.mvc.parameters.HttpPostParam;
import org.zdevra.guice.mvc.parameters.HttpSessionParam;
import org.zdevra.guice.mvc.parameters.InjectorParam;
import org.zdevra.guice.mvc.parameters.ModelParam;
import org.zdevra.guice.mvc.parameters.ParamProcessor;
import org.zdevra.guice.mvc.parameters.ParamProcessorFactory;
import org.zdevra.guice.mvc.parameters.ParamProcessorsService;
import org.zdevra.guice.mvc.parameters.RequestParam;
import org.zdevra.guice.mvc.parameters.ResponseParam;
import org.zdevra.guice.mvc.parameters.SessionAttributeParam;
import org.zdevra.guice.mvc.parameters.UriParam;
import org.zdevra.guice.mvc.views.NamedViewScanner;

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
 * @see ViewScannerService
 *  
 */
public abstract class MvcModule extends ServletModule {
	
// ------------------------------------------------------------------------
		
	private static final Logger logger = Logger.getLogger(MvcModule.class.getName());
	
	private ConversionServiceBuilder conversionServiceBuilder;
	private ExceptionResolverBuilder exceptionResolverBuilder;
	private ControllerModuleBuilder controllerModuleBuilder;
	private ParamProcessorBuilder paramProcessorBuilder;
	private ViewScannerBuilder viewScannerBuilder;
	private NamedViewBuilder namedViewBudiler;
	private List<HttpServlet> servlets;

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
		
		conversionServiceBuilder = new ConversionServiceBuilder(binder());
		controllerModuleBuilder = new ControllerModuleBuilder();		
		exceptionResolverBuilder = new ExceptionResolverBuilder(binder());		
		paramProcessorBuilder = new ParamProcessorBuilder(binder());
		viewScannerBuilder = new ViewScannerBuilder(binder());
		namedViewBudiler = new NamedViewBuilder(binder());		
		servlets = new LinkedList<HttpServlet>();
		
		try {
			//default registrations
			bind(ViewResolver.class).to(DefaultViewResolver.class);
			
			bind(ExceptionResolver.class)
				.to(GuiceExceptionResolver.class);
			
			bind(ExceptionHandler.class)
				.annotatedWith(Names.named(ExceptionResolver.DEFAULT_EXCEPTIONHANDLER_NAME))
				.to(DefaultExceptionHandler.class);
			
			bind(ConversionService.class).asEagerSingleton();
			registerConverter(new BooleanConverterFactory());
			registerConverter(new DateConverterFactory());
			registerConverter(new DoubleConverterFactory());
			registerConverter(new LongConverterFactory());
			registerConverter(new FloatConverterFactory());
			registerConverter(new IntegerConverterFactory());
			registerConverter(new StringConverterFactory());
						
			bind(ParamProcessorsService.class);
			registerParameterProc(HttpPostParam.Factory.class);			
			registerParameterProc(UriParam.Factory.class);
			registerParameterProc(SessionAttributeParam.Factory.class);
			registerParameterProc(ModelParam.Factory.class);
			registerParameterProc(RequestParam.Factory.class);
			registerParameterProc(ResponseParam.Factory.class);
			registerParameterProc(HttpSessionParam.Factory.class);
			registerParameterProc(InjectorParam.Factory.class);
			
			
			bind(ViewScannerService.class);
			registerViewScanner(NamedViewScanner.class);
			
			configureControllers();
			
			//register MVC controllers
			List<ServletDefinition> defs = controllerModuleBuilder.getControllerDefinitions();
			if (defs.size() == 0) {
				logger.log(Level.WARNING, "None controller has been defined in the MVC module");
			}
			
			for (ServletDefinition def : defs) {
				String pattern = def.getUrlPattern();				
				HttpServlet servlet = def.createServlet(binder());
				requestInjection(servlet);
				serve(pattern).with(servlet);
				servlets.add(servlet);
			}			
			
		} finally {
			exceptionResolverBuilder = null;
			controllerModuleBuilder = null;
			viewScannerBuilder = null;
			paramProcessorBuilder = null;
			namedViewBudiler = null;
			conversionServiceBuilder = null;
		}
	}
		
// ------------------------------------------------------------------------
	
	/**
	 * This method can be used only for testing purpose
	 */
	public final List<HttpServlet> getServlets() {
		return this.servlets;
	}

	
	/**
	 * Method bind to view's name some view.
	 */
	protected final NamedViewBindingBuilder bindViewName(String viewName) {
		return this.namedViewBudiler.bindViewName(viewName);
	}
		
	
	/**
	 * The method registers a custom converter which converts strings to the
	 * concrete types. These converters are used for conversions from a HTTP request 
	 * to the method's parameters.
	 * 
	 * The all predefined default converters are placed in the 'converters' sub-package.  
	 */
	protected final void registerConverter(ConverterFactory converterFactory) {
		this.conversionServiceBuilder.registerConverter(converterFactory);
	}
	
	
	/**
	 * The method registers a custom converter which converts strings to the
	 * concrete types. These converters are used for conversions from a HTTP request 
	 * to the method's parameters.
	 * 
	 * The all predefined default convertors are placed in the 'converters' sub-package.  
	 */
	protected final void registerConverter(Class<? extends ConverterFactory> convertorFactoryClazz) {
		this.conversionServiceBuilder.registerConverter(convertorFactoryClazz);
	}
	
		
	/**
	 * The method register into {@link ViewScannerService} a custom
	 * view scanner as a class
	 * 
	 * @see ViewScannerService
	 */
	protected final void registerViewScanner(Class<? extends ViewScanner> scannerClass) {
		this.viewScannerBuilder.as(scannerClass);
	}
	
	
	/**
	 * The method register into {@link ViewScannerService} a custom
	 * view scanner as a instance.
	 * 
	 * @see ViewScannerService
	 */
	protected final void registerViewScanner(ViewScanner scannerInstance) {
		this.viewScannerBuilder.asInstance(scannerInstance);
	}	
	
	
	/**
	 * The method registers a custom parameter processor. The parameter processors
	 * converts/prepares/fills the values into invoked method's parameters.  
	 * All predefined processors are placed in 'parameters' sub-package.
	 * 
	 * @param paramProcFactory
	 * 
	 * @see ParamProcessorFactory
	 * @see ParamProcessor
	 */
	protected final void registerParameterProc(Class<? extends ParamProcessorFactory> paramProcFactory) {
		paramProcessorBuilder.registerParamProc(paramProcFactory);
	}
	
	
	/**
	 * Method binds the exception handler to concrete exception type
	 * @param exceptionClazz
	 * @return
	 */
	protected final ExceptionResolverBindingBuilder bindException(Class<? extends Throwable> exceptionClazz) {
		return this.exceptionResolverBuilder.bindException(exceptionClazz);
	}
	
	
	/**
	 * Method bind controller class to the concrete url.
	 * @param urlPattern
	 * @return
	 */
	protected final ControllerAndViewBindingBuilder control(String urlPattern) 
	{
		return this.controllerModuleBuilder.control(urlPattern);
	}
	
// ------------------------------------------------------------------------	
	
	public static interface ControllerBindingBuilder {
		public ControllerBindingBuilder withController(Class<?> controller);
	}
	
	
	public static interface ControllerAndViewBindingBuilder {
		public ControllerBindingBuilder withController(Class<?> controller);
		public void withView(String name);
		public void withView(View viewInstance);
	}		
	
	
	public static interface ExceptionResolverBindingBuilder {
		public void toHandler(Class<? extends ExceptionHandler> handlerClass);
		public void toHandlerInstance(ExceptionHandler handler);
		public void toErrorView(String viewName);
		public void toErrorView(View errorView);
	}
	
	
	public static interface NamedViewBindingBuilder {
		public void toView(Class<? extends View> viewCLass);
		public void toViewInstance(View view);
		public void toJsp(String pathToFile);
	}
		
// ------------------------------------------------------------------------
}
