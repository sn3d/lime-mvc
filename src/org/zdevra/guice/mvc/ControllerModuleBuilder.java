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

import java.util.LinkedList;
import java.util.List;

import org.zdevra.guice.mvc.MvcModule.ControllerAndViewBindingBuilder;
import org.zdevra.guice.mvc.MvcModule.ControllerBindingBuilder;
import org.zdevra.guice.mvc.views.NamedView;



/** 
 * Builder provide building of controller. It's used
 * internally by {@link MvcModule}
 * 
 * @see MvcModule
 */
class ControllerModuleBuilder  {
	
// ------------------------------------------------------------------------
	
	private String actualUrlPattern;
	private ControllerDefinition actualControllerDefinition = null;
	private List<ServletDefinition> servletDefinitions = new LinkedList<ServletDefinition>();
	
// ------------------------------------------------------------------------
	
	private class ControllerBindingBuilderImpl implements ControllerBindingBuilder {				
		@Override
		public final ControllerBindingBuilder withController(Class<?> controller) {			
			actualControllerDefinition.addController(controller);
			return this;
		}					
	}
	
	private class ControllerAndViewBindingBuilderImpl implements ControllerAndViewBindingBuilder {

		@Override
		public ControllerBindingBuilder withController(Class<?> controller) {
			if (actualControllerDefinition != null) {
				servletDefinitions.add(actualControllerDefinition);
			}
			actualControllerDefinition = new ControllerDefinition(actualUrlPattern);
			actualControllerDefinition.addController(controller);
			return new ControllerBindingBuilderImpl();
		}

		@Override
		public void withView(String name) {
			ServletDefinition def = new DirectViewDefinition(actualUrlPattern, NamedView.create(name));
			servletDefinitions.add(def);
		}

		@Override
		public void withView(View viewInstance) {
			ServletDefinition def = new DirectViewDefinition(actualUrlPattern, viewInstance);
			servletDefinitions.add(def);			
		}
		
	}
	
	
// ------------------------------------------------------------------------
		
	public final ControllerAndViewBindingBuilder control(String urlPattern) {
		actualUrlPattern = urlPattern;
		return new ControllerAndViewBindingBuilderImpl();
	}
	
	
	public List<ServletDefinition> getControllerDefinitions() {
		if (actualControllerDefinition != null) {
			servletDefinitions.add(actualControllerDefinition);
		}
		return servletDefinitions;
	}
	
// ------------------------------------------------------------------------
	
}
