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
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;

import com.google.inject.Binder;


/**
 * The class represent definition of controllers in controller building process
 * and creates the MvcDispatcherServler as well.
 * 
 * This class is mainly used by {@link ControllerModuleBuilder}
 */
class ControllerDefinition extends ServletDefinition {
	
// ------------------------------------------------------------------------

	private static final Logger logger = Logger.getLogger(ControllerDefinition.class.getName());
    public static final Factory FACTORY = new Factory();
	protected List<Class<?>> controllers;
	protected List<Class<? extends InterceptorHandler>> interceptorHandlers;
	
// ------------------------------------------------------------------------


    /**
     * Hidden constructor. The class is instantiated via ServletDefinition.Factory
     * interface.
     */
	protected ControllerDefinition(String urlPattern) {
		super(urlPattern);
		this.controllers = new ArrayList<Class<?>>(10);
		this.interceptorHandlers = new ArrayList<Class<? extends InterceptorHandler>>(10);
	}


    /**
     * Factory's implementation creates the instance of ControllerDefinition
     */
    public static class Factory {
        public ControllerDefinition create(String urlPattern) {
            return new ControllerDefinition(urlPattern);
        }
    }
	
	
// ------------------------------------------------------------------------
	
	public void addController(Class<?> controller) {
		controllers.add(controller);
	}
	
	
	public void addInterceptorHandler(Class<? extends InterceptorHandler> handler) {
		interceptorHandlers.add(handler);
	}

	
	public List<Class<?>> getControllers() {
		return controllers;
	}
	
	
	@Override
	public HttpServlet createServlet(Binder binder) {
		logger.info("for path '" + getUrlPattern() + "' should be registered follwing controllers: " + this.controllers);
		return new MvcDispatcherServlet(this.controllers, this.interceptorHandlers);
	}
		
// ------------------------------------------------------------------------
}
