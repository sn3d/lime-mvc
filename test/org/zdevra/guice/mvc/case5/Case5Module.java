package org.zdevra.guice.mvc.case5;

import org.zdevra.guice.mvc.MvcModule;
import org.zdevra.guice.mvc.TestView;

public class Case5Module extends MvcModule {

	@Override
	protected void configureControllers() {		
		registerViewScanner(TestViewScanner.class);		
		bindViewName("one.jsp").toViewInstance(new TestView("1"));
		bindViewName("two.jsp").toViewInstance( new TestView("2") );
		bindViewName("three.jsp").toViewInstance( new TestView("3") );
	}

}
