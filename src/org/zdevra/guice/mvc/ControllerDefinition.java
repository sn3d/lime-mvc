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

public class ControllerDefinition {
	
// ------------------------------------------------------------------------
	
	private Class<?> controllerClass;
	private String urlPattern;
	private View defaultView;
	
// ------------------------------------------------------------------------

	public ControllerDefinition() {
		this(null, null);
	}
	
	
	public ControllerDefinition(String urlPattern, Class<?> controllerClass) {
		this.defaultView = View.NULL_VIEW;
		this.controllerClass = controllerClass;
		this.urlPattern = urlPattern;
	}
	
// ------------------------------------------------------------------------

	@Override
	public String toString() {
		return "ControllerDefinition [controllerClass=" + controllerClass
				+ ", urlPattern=" + urlPattern + ", defaultView=" + defaultView
				+ "]";
	}
	
	
	public Class<?> getControllerClass() {
		return controllerClass;
	}

	public String getUrlPattern() {
		return urlPattern;
	}	
	
	public View getDefaultView() {
		return defaultView;
	}
	
	public void setDefaultView(View view) {
		this.defaultView = view;
	}
	
// ------------------------------------------------------------------------
}
