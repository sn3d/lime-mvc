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
package org.zdevra.guice.mvc.views;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zdevra.guice.mvc.HttpRequestForForward;
import org.zdevra.guice.mvc.View;
import org.zdevra.guice.mvc.exceptions.InvalidJspViewException;

public class JspView implements View {
	
/*---------------------------- m. variables ----------------------------*/
	
	private final String viewName;
	
/*---------------------------- constructors ----------------------------*/
	
	public static View create(String viewName) {
		if (viewName == null || viewName.length() == 0) {
			return View.NULL_VIEW;
		}
		
		if ( !viewName.startsWith("/") ) {
			viewName = "/" + viewName;
		}
		
		return new JspView(viewName);		
	}
	
	private JspView(String viewName) {
		this.viewName = viewName;
	}
	
/*------------------------------- methods ------------------------------*/
	
	
	@Override
	public void redirectToView(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request = new HttpRequestForForward(request, viewName);
		RequestDispatcher dispatcher = servlet.getServletContext().getRequestDispatcher(viewName);
		if (dispatcher == null) {
			throw new InvalidJspViewException(viewName);
		}
		
		dispatcher.forward(request, response);
		request = null;
	}

	
	@Override
	public String toString() {
		return "JspView [viewName=" + viewName + "]";
	}
	
/*----------------------------------------------------------------------*/

}
