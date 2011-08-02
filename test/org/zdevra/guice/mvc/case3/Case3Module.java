package org.zdevra.guice.mvc.case3;

import org.zdevra.guice.mvc.MvcModule;
import org.zdevra.guice.mvc.TestView;

public class Case3Module extends MvcModule {

	@Override
	protected void configureControllers() {
		bindView("one",   new TestView("1"));
        bindView("two",   new TestView("2"));
        bindView("three", new TestView("3"));		
	}

}
