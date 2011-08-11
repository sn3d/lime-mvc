package org.zdevra.guice.mvc.case1;

import org.zdevra.guice.mvc.MvcModule;
import org.zdevra.guice.mvc.TestView;

public class Case1Module extends MvcModule {

	@Override
	protected void configureControllers() {
		bindViewName("default").toViewInstance(new TestView("0"));
	}

}
