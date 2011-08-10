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

import java.util.logging.Logger;
import org.zdevra.guice.mvc.MvcModule.ControllerBindingBuilder;



/** 
 * Builder provide building of controller. It's used
 * internally by {@link MvcModule}
 * 
 * @see MvcModule
 */
class ControllerModuleBuilder  {
	
// ------------------------------------------------------------------------
	
	private static final Logger logger = Logger.getLogger(ControllerBindingBuilder.class.getName());
	
	private List<ControllerDefinition> controllerDefinitions = new LinkedList<ControllerDefinition>();
	
// ------------------------------------------------------------------------
	
	private class ControllerBindingBuilderImpl implements ControllerBindingBuilder {
		
		private String urlPattern;
		private ControllerDefinition actualDefinition;
		
		public ControllerBindingBuilderImpl(String urlPattern) {
			this.urlPattern = urlPattern;
		}
		
		@Override
		public final void withController(Class<?> controller) {
			actualDefinition = new ControllerDefinition(urlPattern, controller);
			logger.info("register controller " + actualDefinition.toString());
			controllerDefinitions.add(actualDefinition);
		}					
	}
	
	
// ------------------------------------------------------------------------
		
	public final ControllerBindingBuilder control(String urlPattern) {
		return new ControllerBindingBuilderImpl(urlPattern);
	}
	
	
	public List<ControllerDefinition> getControllerDefinitions() {
		return controllerDefinitions;
	}
	
// ------------------------------------------------------------------------
	
}
