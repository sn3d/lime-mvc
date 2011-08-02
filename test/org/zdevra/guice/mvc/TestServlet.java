package org.zdevra.guice.mvc;

import com.google.inject.Guice;

public class TestServlet extends MvcDispatcherServlet {
	public TestServlet(Class<?> controllerClass, MvcModule module) {
		super(controllerClass, new TestView("0"),  Guice.createInjector( module ) );
	}
}
