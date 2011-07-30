package org.zdevra.guice.mvc.case3;

import org.zdevra.guice.mvc.MvcDispatcherServlet;
import org.zdevra.guice.mvc.MvcModule;
import org.zdevra.guice.mvc.TestView;

import com.google.inject.Guice;

class Case3Module extends MvcModule {


	@Override
	protected void configureControllers() {
		bindView("one",   new TestView("1"));
		bindView("two",   new TestView("2"));
		bindView("three", new TestView("3"));
	}
	
}

public class Case3DispatcherServlet extends MvcDispatcherServlet {

	public Case3DispatcherServlet() 
	{
		super(
			Case3Controller.class, 
			new TestView("0"), 
			Guice.createInjector(new Case3Module()) ); 
	}
}
