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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Workaround for ServletDispatcher's forward and guice's request object.
 * <p>
 * 
 * Problematic part was a forwarding from dispatcher servlet to jsp or html (or
 * any content). In Guice's case is used special request object {@link ServletDefinition}
 * which is doing special calculations and operations over getPathInfo() method.
 * <p>
 *  
 * This class only wrap Guice's request object and injecting special result
 * for getPathInfo() and getServletPath() methods. Without this workaround
 * was not possible correctly forwarding servlet to any other content in web app.
 * <p>
 *
 */
class HttpRequestForForward extends HttpServletRequestWrapper {
	
	private String pathForForward;

	public HttpRequestForForward(HttpServletRequest request, String pathForForward) {
		super(request);
		this.pathForForward = pathForForward;
	}

	@Override
	public String getPathInfo() {
		super.getPathInfo();		
		return pathForForward;		
	}

	@Override
	public String getServletPath() {
		super.getServletPath();
		return "";
	}
	
	
}
