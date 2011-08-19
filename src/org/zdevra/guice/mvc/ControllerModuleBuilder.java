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

import org.zdevra.guice.mvc.MvcModule.ControllerBindingBuilder;



/** 
 * Builder provide building of controller. It's used
 * internally by {@link MvcModule}
 * 
 * @see MvcModule
 */
class ControllerModuleBuilder  {
	
// ------------------------------------------------------------------------
		
	private ControllerDefinition actualDefinition = null;
	private List<ControllerDefinition> controllerDefinitions = new LinkedList<ControllerDefinition>();
	
// ------------------------------------------------------------------------
	
	private class ControllerBindingBuilderImpl implements ControllerBindingBuilder {
		
		
		@Override
		public final ControllerBindingBuilder withController(Class<?> controller) {
			actualDefinition.addController(controller);
			return this;
		}					
	}
	
	
// ------------------------------------------------------------------------
		
	public final ControllerBindingBuilder control(String urlPattern) {
		if (actualDefinition != null) {
			controllerDefinitions.add(actualDefinition);
		}
		
		actualDefinition = new ControllerDefinition(urlPattern);
		return new ControllerBindingBuilderImpl();
	}
	
	
	public List<ControllerDefinition> getControllerDefinitions() {
		if (actualDefinition != null) {
			controllerDefinitions.add(actualDefinition);
		}
		return controllerDefinitions;
	}
	
// ------------------------------------------------------------------------
	
}
