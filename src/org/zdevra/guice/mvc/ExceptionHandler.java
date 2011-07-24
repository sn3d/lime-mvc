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

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Abstraction for all exception handlers. All handlers are
 * registered in {@link ExceptionResolver} via
 * {@link MvcModule}.
 * <p>
 * 
 * Method handleException is invoked in case when controller throw some
 * kind of exception which is instance of exceptionClass.
 * <p>
 *
 * <pre>
 * Warn: Please avoid to use any states or member variables in handler, or
 *       minimize it and use final keyword. Handler's instance is existing only
 *       one per servlet instance and handler's method handleException() is
 *       called parallelly from different threads.
 * </pre>
 */
public abstract  class ExceptionHandler {
		
/*------------------------------- methods ------------------------------*/
	
	/**
	 * Is invoked when in the controller was throwed exception which 
	 * is instance of registered class.
	 * 
	 * @return when method returns true, the resolver stop working 
	 *         when method returns false, the resolver continue going through other handlers
	 */
	public abstract boolean handleException(Throwable t, HttpServlet servlet, HttpServletRequest req, HttpServletResponse resp);
	
/*----------------------------------------------------------------------*/

}
