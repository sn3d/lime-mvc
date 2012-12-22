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
	private ControllerDefinition actualControllersDefinition = null;
	private List<ServletDefinition> servletDefinitions = new LinkedList<ServletDefinition>();
	
// ------------------------------------------------------------------------
	
	private class ControllerBindingBuilderImpl implements ControllerBindingBuilder {
		
		@Override
		public final ControllerBindingBuilder withController(Class<?> controller) {			
			actualControllersDefinition.addController(controller);
			return this;
		}		
		
		@Override
		public ControllerBindingBuilder interceptor(Class<? extends InterceptorHandler> handlerClass) {
			actualControllersDefinition.addInterceptorHandler(handlerClass);
			return this;
		}
	}
	
	private class ControllerAndViewBindingBuilderImpl implements ControllerAndViewBindingBuilder {

        private ControllerDefinition.Factory definitionFactory;

        public ControllerAndViewBindingBuilderImpl(ControllerDefinition.Factory definitionFactory) {
            this.definitionFactory = definitionFactory;
        }

		@Override
		public ControllerBindingBuilder withController(Class<?> controller)
        {
            //store prev. controller definition
			if (actualControllersDefinition != null) {
				servletDefinitions.add(actualControllersDefinition);
			}

            //create new actual definition.
            actualControllersDefinition = definitionFactory.create(actualUrlPattern);
			actualControllersDefinition.addController(controller);

			return new ControllerBindingBuilderImpl();
		}

        @Override
		public void withView(String name) {
			ServletDefinition def = new DirectViewDefinition(actualUrlPattern, NamedView.create(name));
			servletDefinitions.add(def);
		}

		@Override
		public void withView(ViewPoint viewInstance) {
			ServletDefinition def = new DirectViewDefinition(actualUrlPattern, viewInstance);
			servletDefinitions.add(def);			
		}
		
	}
	
	
// ------------------------------------------------------------------------
		
	public final ControllerAndViewBindingBuilder control(String urlPattern) {
		actualUrlPattern = urlPattern;
		return new ControllerAndViewBindingBuilderImpl(ControllerDefinition.FACTORY);
	}


	public List<ServletDefinition> getControllerDefinitions() {
		if (actualControllersDefinition != null) {
			servletDefinitions.add(actualControllersDefinition);
		}
		return servletDefinitions;
	}
	
// ------------------------------------------------------------------------
	
}
