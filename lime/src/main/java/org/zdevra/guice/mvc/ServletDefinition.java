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

import javax.servlet.http.HttpServlet;

import com.google.inject.Binder;

/**
 * Class is representing data-structure used internally by Lime and
 * compose the all necessary data for servlet.
 */
abstract class ServletDefinition {

// ------------------------------------------------------------------------
	
	private final String urlPattern;
	
// ------------------------------------------------------------------------
	
	public abstract HttpServlet createServlet(Binder binder);
	
// ------------------------------------------------------------------------
	
	public ServletDefinition(String urlPattern) {
		this.urlPattern = urlPattern;
	}
	
	
	public final String getUrlPattern() {
		return urlPattern;
	}
	
// ------------------------------------------------------------------------
}