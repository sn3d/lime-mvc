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
package org.zdevra.guice.mvc.exceptions;

import javax.servlet.http.HttpServletRequest;

/**
 * The lime throws this exception when no method is invoked for URL request.
 *
 */
public class NoMethodInvoked extends MvcException {
	
	public NoMethodInvoked(HttpServletRequest request) {
		super("no method has been invoked for the url:" + request.getRequestURL().toString());
	}

	public NoMethodInvoked(HttpServletRequest request, String controllerName) {
		super("no method in the controller " + controllerName + " has been invoked for the url:" + request.getRequestURL().toString());
	}

}
