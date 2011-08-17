package org.zdevra.guice.mvc.case6;

import org.zdevra.guice.mvc.MvcModule;

public class Case6Module extends MvcModule {

	@Override
	protected void configureControllers() {
		bindViewName("cars.jsp").toViewInstance(new Case6View("cars.jsp") );
		bindViewName("people.jsp").toViewInstance(new Case6View("people.jsp") );
	}

}
