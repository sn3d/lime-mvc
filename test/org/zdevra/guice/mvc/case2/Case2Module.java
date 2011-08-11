package org.zdevra.guice.mvc.case2;

import org.zdevra.guice.mvc.MvcModule;
import org.zdevra.guice.mvc.TestView;

public class Case2Module extends MvcModule {

	@Override
	protected void configureControllers() {
		bindViewName("default").toViewInstance(new TestView("0"));
		bind(Case2Controller.class).to(Case2ControllerImpl.class);
	}

}
