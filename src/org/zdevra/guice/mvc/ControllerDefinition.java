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

import java.util.ArrayList;
import java.util.List;


/**
 * Class is representing data-structure used internally by Lime and
 * compose the all necessary data for controller.
 * 
 * This class is mainly used by {@link MvcDispatcherServlet}
 */
class ControllerDefinition {
	
// ------------------------------------------------------------------------
	
	private List<Class<?>> controllers;
	private String urlPattern;
	
// ------------------------------------------------------------------------

	
	public ControllerDefinition(String urlPattern) {
		this.urlPattern = urlPattern;
		this.controllers = new ArrayList<Class<?>>(10);
	}
	
	
// ------------------------------------------------------------------------
	
	public void addController(Class<?> controller) {
		controllers.add(controller);
	}

	
	public List<Class<?>> getControllers() {
		return controllers;
	}

	
	public String getUrlPattern() {
		return urlPattern;
	}	
		
// ------------------------------------------------------------------------
}
