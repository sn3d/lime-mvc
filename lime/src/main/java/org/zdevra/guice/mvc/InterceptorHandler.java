/*****************************************************************************
 * Copyright 2012 Zdenko Vrabel
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface you will implement when you want to do some global 
 * pre/post processing of the requests and responses. Interceptors are executed in
 * chain. When one of the interceptor's preHandle() method returns false, then the
 * processing of the request will stop and no postHandle and afterCompletion shouldn't 
 * be invoked.
 * 
 * <br><p>
 * 
 * In general, there are 2 ways how to register interceptor handlers. As a global handlers
 * which is invoked for all controllers, or as a mapped interceptor which is invoked for concrete 
 * controler of group of controllers.
 * 
 * <p>example of the global interceptor handler registration:
 * <pre class="prettyprint">
 * public class MyWebAppModule extends MvcModule {
 *   protected void configureControllers() {
 *     ...
 *     registerGlobalInterceptor(LogInterceptor.class);
 *     registerGlobalInterceptor(SecurityInterceptor.class);
 *     ...
 *   }
 * }   
 * </pre>
 * 
 * 
 * <p>example of the mapped interceptor handler registration:
 * <pre class="prettyprint">
 * public class MyWebAppModule extends MvcModule {
 *   protected void configureControllers() {
 *     ...
 *     control("/somepath")
 *        .withController(FirstController.class)
 *        .withController(SecondController.class)
 *        .interceptor(SecurityInterceptor.class);
 *     ...
 *   }
 * }   
 *
 * </pre>
 * 
 * If you need implement only pre-handling or only post-handling , you colud use the AbstractInterceptorHandler 
 * which simplify the implementation. 
 *  
 */
public interface InterceptorHandler {

    /**
     * Dispatcher calls the preHandler before controller's method invokation.
     * 
     * @param request
     * @param response
     * 
     * @return true if the execution should continue in chain execution, false if you want to 
     * stop excution of the interceptor chain. The controller shouldn't be invoked when the 
     * interceptor returns false and the request's processing ends.
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response);

    /**
     * Dispatcher calls the postHandler after controller's method execution but before
     * the view is rendering.
     * 
     * @param request
     * @param response
     * @param mav
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, ModelAndView mav);

    /**
     * Method is invoked at the end of request processing when the view has been rendered and only in
     * case when all interceptors return true.
     * 
     * You will implement here resource releasing.  
     * 
     * @param request
     * @param response
     * @param e
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Throwable e);
}
