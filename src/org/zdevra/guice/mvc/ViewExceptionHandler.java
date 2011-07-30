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

import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This handler's implementation redirect exception to the some 
 * error-view. Exception is stored in a request's attribute 
 * called 'exception' and you can access to this value via
 * request.getAttribute("exception") in JSP.
 * 
 */
public class ViewExceptionHandler extends ExceptionHandler {
	
/*---------------------------- m. variables ----------------------------*/
	
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(MvcDispatcherServlet.class.getName());	
	private final View exceptionView;

/*---------------------------- constructors ----------------------------*/
		
	/**
	 * Constructor
	 * @param exceptionView
	 */
	public ViewExceptionHandler(View exceptionView) {
		this.exceptionView = exceptionView;
	}
	
/*------------------------------- methods ------------------------------*/
	
	@Override
	public boolean handleException(Throwable t, HttpServlet servlet, HttpServletRequest req, HttpServletResponse resp) {		
		req.setAttribute("exception", t);
		exceptionView.render(servlet, req, resp);					
		return true;
	}
	
/*----------------------------------------------------------------------*/
}
