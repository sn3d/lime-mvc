package org.zdevra.guice.mvc;

import org.zdevra.guice.mvc.case4.Case4Controller;

import com.google.inject.Guice;

public class TestServlet extends MvcDispatcherServlet {
	public TestServlet(Class<?> controllerClass, MvcModule module) {
		super(Case4Controller.class, new TestView("0"),  Guice.createInjector( module ) );
	}
}
