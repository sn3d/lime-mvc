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
 * instance into Guice as singleton. 
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
	protected abstract void configureControllers(Configuration freemakerConfiguration) throws Exception;
	
// ------------------------------------------------------------------------
	
	@Override
	protected final void configureControllers() throws Exception {
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
