package org.zdevra.guice.mvc.case4;

import org.zdevra.guice.mvc.MvcModule;

public class Case4Module extends MvcModule {

	@Override
	protected void configureControllers() {		
		bindException(CustomException.class).toHandler(CustomExceptionHandler.class);
		bindException(AdvancedHandledException.class).toHandlerInstance(new AdvancedHandler());
	}

}
