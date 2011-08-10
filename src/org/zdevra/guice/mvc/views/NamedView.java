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

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zdevra.guice.mvc.Controller;
import org.zdevra.guice.mvc.View;

/**
 * This view does nothing His purpose is to identify. All named views are handled by view resolver
 * which decide what view should be used. 
 * 
 * The named view is something like a default view used by Lime MVC. All view are identified
 * in the Lime MVC by names. After that, Lime MVC resolve view's name to concrete 
 * view.
 * 
 * Prefer this view as a controller's return view, because only named views
 * are handled by the Lime view resolver.
 * 
 * @see org.zdevra.guice.mvc.ViewResolver
 * @see org.zdevra.guice.mvc.MvcModule
 */
public final class NamedView implements View {
	
// ------------------------------------------------------------------------
	
	private final String name;

// ------------------------------------------------------------------------
	
	/**
	 * Method construct named view from {@literal @}Controller annotation
	 * and his parameter toView.
	 *  
	 * @param controllerClass
	 * @return instance of NamedView
	 */
	public static View create(Class<?> controllerClass) {
		Controller controllerAnotation = controllerClass.getAnnotation(Controller.class);
		if (controllerAnotation != null) {
			String viewName = controllerAnotation.toView();
			if (viewName != null && viewName.length() > 0) {
				return new NamedView(viewName);
			}
		}				
		return View.NULL_VIEW;
	}
	
	
	public static View create(String name) {
		if (name == null || name.length() == 0) {
			return View.NULL_VIEW;
		}
		
		return new NamedView(name);
	}
	
	
	private NamedView(String name) {
		this.name = name;
	}
	
// ------------------------------------------------------------------------
	
	public String getName() {
		return this.name;
	}

	@Override
	public final void render(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
		//do nothing
	}
	
	@Override
	public String toString() {
		return "NamedView [name=" + name + "]";
	}

		
// ------------------------------------------------------------------------
}
