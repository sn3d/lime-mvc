package org.zdevra.guice.mvc.velocity;

import org.apache.velocity.app.VelocityEngine;
import org.zdevra.guice.mvc.MvcModule;

public abstract class VelocityModule extends MvcModule {
	
	protected abstract void configureControllers(VelocityEngine velocity);

	@Override
	protected final void configureControllers() throws Exception 
	{		
		VelocityEngine velocity = new VelocityEngine();	    
	    velocity.addProperty("file.resource.loader.path", getServletContext().getRealPath("/"));	    
	    configureControllers(velocity);	    
	    velocity.init();
	}
	
	
	protected final void bindViewNameToVelocity(String viewName, String velocityFile) {
		bindViewName(viewName).toViewInstance( new VelocityView(velocityFile) );
	}
	
}
