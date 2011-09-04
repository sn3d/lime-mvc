package org.zdevra.guice.mvc.case8;

import org.zdevra.guice.mvc.MvcModule;
import org.zdevra.guice.mvc.TestView;

public class Case8Module extends MvcModule {

	@Override
	protected void configureControllers() {
		bindViewName("default").toViewInstance(new TestView("0"));
	}

}
