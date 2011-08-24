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
package org.zdevra.guice.mvc.freemarker;

import java.io.IOException;

import org.zdevra.guice.mvc.MvcModule;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

/**
 * Use this Lime MVC Module if you want to use Freemarker
 * template engine for rendering views.
 * 
 * The module register and bind freemarker's Configuration
 * instance into Guice as a singleton. 
 * 
 * @see MvcModule
 */
public abstract class FreemarkerModule extends MvcModule {
	
// ------------------------------------------------------------------------
	
	private Configuration conf; 
	
// ------------------------------------------------------------------------
	
	/**
	 * You will implement the MVC configuration and setup into this method
	 *  
	 * @param freemakerConfiguration
	 */
	protected abstract void configureControllers(Configuration freemakerConfiguration);
	
// ------------------------------------------------------------------------
	
	@Override
	protected final void configureControllers() {
		try {
			//create freemaker's configuration
			conf = new Configuration();
			conf.setServletContextForTemplateLoading(getServletContext(), "/");
			conf.setObjectWrapper(new DefaultObjectWrapper());				
			bind(Configuration.class).toInstance(conf);		
			
			registerViewScanner(FreemarkerScanner.class);			
			configureControllers(conf);
		} finally {
			conf = null;
		}
	}
		
// ------------------------------------------------------------------------
	
	protected final void bindViewNameToFreemarker(String viewName, String freemarkerFile) throws IOException {
		bindViewName(viewName).toViewInstance( new FreemarkerView(conf, freemarkerFile) );
	}

// ------------------------------------------------------------------------	
}
