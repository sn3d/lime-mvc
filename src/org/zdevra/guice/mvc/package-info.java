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

/**
 * The core package of Lime contains main classes like {@link org.zdevra.guice.mvc.MvcModule} and interfaces which
 * can be implemented for custom functionality.
 * 
 * <p> <b>The main API are:</b>
 * <dl>
 * 
 * <dt>{@link org.zdevra.guice.mvc.MvcModule}
 * <dd>The core abstraction class you will implement for configuration how your MVC will use 
 *     the controllers, handle exceptions, use views.
 * 
 * <dt>{@link org.zdevra.guice.mvc.View}
 * <dd>The interface you will implement for custom views.
 * 
 * <dt>{@link org.zdevra.guice.mvc.Model}
 * <dd>The class representing model which is produced by the controller.
 * 
 * <dt>{@link org.zdevra.guice.mvc.ModelAndView}
 * <dd>The class representing model and view.
 * 
 * <dt>{@link org.zdevra.guice.mvc.ExceptionHandler}
 * <dd>The interface you will implement for custom views.
 *
 * </dl>
 * 
 * 
 * <p><b>Annotations for controller classes and his methods:</b>  
 * <dl>
 * 
 * <dt>{@link org.zdevra.guice.mvc.Controller}
 * <dd>The main annotation marks the class as a controller
 * 
 * <dt>{@link org.zdevra.guice.mvc.RequestMapping}
 * <dd>The main annotation for controller's method. This annotation define when is method invoked, 
 *     how is the result processed, which view is used.
 *     
 * <dt>{@link org.zdevra.guice.mvc.RequestParameter}
 * <dd>This is parameter's annotation. The value for parameter will be picked up from
 *     POSTed data from FORMs tags.
 *     
 * <dt>{@link org.zdevra.guice.mvc.UriParameter}
 * <dd>This is parameter's annotation. The value for parameter will be picked up from
 *     request's URL.
 *
 * <dt>{@link org.zdevra.guice.mvc.SessionParameter}
 * <dd>This is parameter's annotation. The value for parameter will be picked up from
 *     session.
 *     
 * </dl>
 * 
 * <p><h3>Main concept</h3>
 * <p><img src="concept-main.png" /><p>
 * 
 * The main concept is regular MVC described in many articles and in the SUN's blueprint as well. The core of Lime MVC is 
 * Servlet dispatcher which provide all necessary conversions, invokes the Controller's methods,
 * and sendg produced model to the selected view. 
 * 
 * 
 * 
 * <p><h3>Exception handling</h3>
 * <p><img src="concept-exceptions.png" /><p>
 * 
 * The Lime has also mechanism of exception handling. The Controller, or the Lime itself 
 * may throws exceptions and normally do. Exception is caught and subsequently sent 
 * to exception resolver. The resolver make decission based on exception's class which 
 * handler handle the exception. You may have your own handlers binded to various exceptions.
 * The binding is realized in MvcModule. 
 * 
 * <br><p>Example: how to bind an exception handler to some exception type
 * <pre class="prettyprint">
 * public class MyWebAppModule extends MvcModule {
 *   protected void configureControllers() {
 *     ...
 *     bindException(CustomException.class).toHandler(CustomExceptionHandler.class);
 *     ...	
 *   }
 * }
 * </pre>
 * 
 * In case, when is throwed some exception and in the Lime is no binding to the handler,
 * then is used default exception handler. Its regular handler registered in Guice with
 * named annotation. If you want to cahnge the default handler, You will register your
 * custom handler under name {@code ExceptionResolver.DEFAULT_EXCEPTIONHANDLER_NAME}.<br> 
 * 
 * <br><p>Example: how to use custom default handler
 * <pre class="prettyprint">
 * public class MyWebAppModule extends MvcModule {
 *   protected void configureControllers() {
 *     ...
 *     bind(ExceptionHandler.class)
 *       .annotatedWith(Names.named(ExceptionResolver.DEFAULT_EXCEPTIONHANDLER_NAME))
 *       .to(DefaultExceptionHandler.class);
 *   }
 * }
 * </pre>
 * 
 * If you're not happy with Exception Resolver's behavior, you may write your own 
 * resolver. You will implement {@link org.zdevra.guice.mvc.ExceptionResolver} and
 * you will register your resolver in the module.
 *  
 * <br><p>Example: how to register your own exception resolver
 * <pre class="prettyprint">
 * public class MyWebAppModule extends MvcModule {
 *   protected void configureControllers() {
 *     ...
 *     bind(ExceptionResolver.class).to(CustomExceptionResolver.class);
 *     ...
 *   }
 * } 
 * </pre>
 * 
 * <p><h3>View resolver</h3>
 * In SUN's blueprint is the controller responsible for a view selection. This principe
 * is used in Lime as well. You may in your controller return concrete view.
 *  
 * <p><img src="concept-custom-view.png" /><p>
 * 
 * This concept is fine and it wokrs. Sometimes is usefull to select and identify 
 * views in your controllers as names and have some resolver which translate these
 * names to concrete views. 
 * 
 * <p><img src="concept-view-resolver.png" /><p>
 * 
 * In your controller, you may select and return view as a {@link org.zdevra.guice.mvc.views.NamedView}.
 * Lime then forwards a model to the View Resolver first. The View resolver pick up a concrete view 
 * for this name identifier and forward the model into this view. 
 * 
 * <br><p>Example:how a controller uses named views
 * <pre class="prettyprint">
 * {@literal @}Controller
 * public class MyController {
 *    {@literal @}RequestMapping(path="/users", toView="users_view")
 *    public Model getUsers() {
 *    ...
 *    }
 *    
 *    {@literal @}RequestMapping(path="/books")
 *    public ModelAndView getBooks() {
 *       ...
 *       return ModelAndView(model, NamedView.create("books_view");
 *    }
 * }
 * </pre>
 * 
 * <br><p>Example: how to bind a view to a view's name
 * <pre class="prettyprint">
 * public class MyWebAppModule extends MvcModule {
 *   protected void configureControllers() {
 *     ...
 *     bindViewName("users_view").toJsp("users.jsp");
 *     bindViewName("books_view").toViewInstance(new JspView("books_view") );
 *     bindVIewName("custom_view").toView(CustomView.class);
 *     ...
 *   }
 * }  
 * </pre>
 * 
 * If you want some special own behavior of the view resolving, you will implement
 * the {@link org.zdevra.guice.mvc.ViewResolver} and you will register your
 * own custom resolver in the module.
 * 
 * <br><p>Example: how to register your own view resolver
 * <pre class="prettyprint">
 * public class MyWebAppModule extends MvcModule {
 *   protected void configureControllers() {
 *     ...
 *     bind(ViewResolver.class).to(CustomViewResolver.class);
 *     ...
 *   }
 * } 
 * </pre>
 */
package org.zdevra.guice.mvc;


