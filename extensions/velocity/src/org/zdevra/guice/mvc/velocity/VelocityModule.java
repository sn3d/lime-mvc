package org.zdevra.guice.mvc.velocity;

import org.apache.velocity.app.VelocityEngine;
import org.zdevra.guice.mvc.MvcModule;

/**
 * Use this Lime MVC Module if you want to use Velocity
 * template engine for rendering views.
 * 
 * The module register and bind Velocity engine
 * instance into Guice.
 * 
 * @see VelocityView
 * @see ToVelocityView
 */
public abstract class VelocityModule extends MvcModule {
	
// ------------------------------------------------------------------------
	
	/**
	 * You will implement this method for your MVC configuration
	 */
	protected abstract void configureControllers(VelocityEngine velocity);
	
// ------------------------------------------------------------------------

	@Override
	protected final void configureControllers() throws Exception 
	{		
		VelocityEngine velocity = new VelocityEngine();
		velocity.addProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
	    velocity.addProperty("file.resource.loader.path", getServletContext().getRealPath("/"));	    
	    configureControllers(velocity);	    
	    velocity.init();	    
	    bind(VelocityEngine.class).toInstance(velocity);
	}
	
// ------------------------------------------------------------------------
	
	/**
	 * Method creates binding between view's name and concrete velocity template
	 * 
	 * @param viewName
	 * @param velocityFile
	 */
	protected final void bindViewNameToVelocity(String viewName, String velocityFile) {
		bindViewName(viewName).toViewInstance( new VelocityView(velocityFile) );
	}
	
// ------------------------------------------------------------------------	
}
