package org.zdevra.guice.mvc.case9;

import org.zdevra.guice.mvc.MvcModule;

public class Case9Module extends MvcModule {

	@Override
	protected void configureControllers() 
	{
		control("/case9/*").withController(Case9Controller.class);
	}

}
