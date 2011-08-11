package org.zdevra.guice.mvc.case3;

import org.zdevra.guice.mvc.MvcModule;
import org.zdevra.guice.mvc.TestView;

class ThreeView extends TestView {

	public ThreeView() {
		super("3");
	}
	
}

public class Case3Module extends MvcModule {

	@Override
	protected void configureControllers() {
		bindViewName("default").toViewInstance(new TestView("0"));
		bindViewName("one").toViewInstance( new TestView("1") );
		bindViewName("two").toViewInstance( new TestView("2") );
		bindViewName("three").toView( ThreeView.class );
	}

}
