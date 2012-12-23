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

import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;

import com.google.inject.Binder;

/**
 * The class represent definition of direct view in module's building process
 * and creates the {@link ViewServlet}.
 * 
 * This class is mainly used by {@link MvcModule}
 */

class DirectViewDefinition extends ServletDefinition {
	
// ------------------------------------------------------------------------
	
	private static final Logger logger = Logger.getLogger(DirectViewDefinition.class.getName());
	private final ViewPoint view;
	
// ------------------------------------------------------------------------
	
	/**
	 * Constructor
	 */
	public DirectViewDefinition(String urlPattern, ViewPoint view) {
		super(urlPattern);
		this.view = view;
	}
	
// ------------------------------------------------------------------------

	@Override
	public HttpServlet createServlet(Binder binder) {
		logger.info("for path '" + getUrlPattern() + "' should be registered follwing direct view " + this.view.toString());
		binder.requestInjection(view);
		return new ViewServlet(view);
	}
	
// ------------------------------------------------------------------------

}
