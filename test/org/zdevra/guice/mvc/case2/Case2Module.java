package org.zdevra.guice.mvc.case2;

import org.zdevra.guice.mvc.MvcModule;

public class Case2Module extends MvcModule {

	@Override
	protected void configureControllers() {
		bind(Case2Controller.class).to(Case2ControllerImpl.class);
	}

}
