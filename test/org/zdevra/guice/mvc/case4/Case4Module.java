package org.zdevra.guice.mvc.case4;

import org.zdevra.guice.mvc.MvcModule;
import org.zdevra.guice.mvc.TestView;

public class Case4Module extends MvcModule {

	@Override
	protected void configureControllers() {		
		bindException(CustomException.class).toHandler(CustomExceptionHandler.class);
		bindException(AdvancedHandledException.class).toHandlerInstance(new AdvancedHandler());
		bindViewName("default").toViewInstance(new TestView("0"));
	}

}
