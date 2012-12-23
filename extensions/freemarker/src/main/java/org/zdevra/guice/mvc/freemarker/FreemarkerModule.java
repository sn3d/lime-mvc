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

import javax.servlet.ServletContext;

import org.zdevra.guice.mvc.MvcModule;
import org.zdevra.guice.mvc.ViewModule;

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
public class FreemarkerModule extends ViewModule {
	
// ------------------------------------------------------------------------
	
	private final ServletContext context;
	private Configuration conf;
	
// ------------------------------------------------------------------------
	
	/**
	 * Constructor ov Freemarker module which loads templates from classpath
	 */
	public FreemarkerModule() {
		this(null);
	}

	/**
	 * Constructor ov Freemarker module which loads template files
	 * from WAR as JSPs.
	 */
	public FreemarkerModule(ServletContext context) {
		this.context = context;
	}
		
// ------------------------------------------------------------------------
	
	/**
	 * You will implement the MVC configuration and setup into this method
	 *  
	 * @param freemakerConfiguration
	 */
	protected void configureFreemarker(Configuration freemakerConfiguration) {
		if (context != null) {
			conf.setServletContextForTemplateLoading(context, "/");
		} else {
			conf.setClassForTemplateLoading(FreemarkerModule.class, "/");
		}		
		conf.setObjectWrapper(new DefaultObjectWrapper());
	}
	
// ------------------------------------------------------------------------
	
	@Override
	protected final void configureViews() {
		try {
			//create freemaker's configuration
			conf = new Configuration();
			configureFreemarker(conf);
			bind(Configuration.class).toInstance(conf);					
			registerViewScanner(FreemarkerScanner.class);						
		} finally {
			conf = null;
		}
	}
		
// ------------------------------------------------------------------------	
}
