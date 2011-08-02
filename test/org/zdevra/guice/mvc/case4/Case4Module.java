package org.zdevra.guice.mvc.case4;

import org.zdevra.guice.mvc.MvcModule;

public class Case4Module extends MvcModule {

	@Override
	protected void configureControllers() {		
		exception(CustomException.class).handledBy(new CustomExceptionHandler());		
		exception(AdvancedHandledException.class).handledBy(new AdvancedHandler());
	}

}
