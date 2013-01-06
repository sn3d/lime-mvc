/**
 * ***************************************************************************
 * Copyright 2011 Zdenko Vrabel
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 ****************************************************************************
 */
package org.zdevra.guice.mvc;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This interface is used for resolving exceptions which are thrown from
 * controllers.
 *
 * The default implementation of resolving in Lime MVC is used
 * {@link GuiceExceptionResolver}. It can be changed easy in
 *
 * @{link MvcModule}'s configureControllers() method.
 *
 *
 *
 * Example: <pre class="prettyprint"> public void configureControllers() { ...
 * bindException(Custom1Exception.class).toHandler(Custom1ExceptionHandler.class);
 * bindException(Custom2Exception.class).toHandlerInstance( new
 * Custom2ExceptionHandler() ); ... }
 * </pre>
 *
 * @see ExceptionHandler
 * @see GuiceExceptionResolver
 * @see MvcModule
 */
public interface ExceptionResolver {

    public static final String DEFAULT_EXCEPTIONHANDLER_NAME = "defaultExceptionHandler";

    /**
     * Method is invoked in {@link MvcDispatcherServlet}, when a controller
     * throw some exception. This method go through all registered exception
     * handlers and invoke correct handler for that exception.
     */
    public abstract void handleException(Throwable t, HttpServlet servlet, HttpServletRequest req, HttpServletResponse resp);
}