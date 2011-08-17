package org.zdevra.guice.mvc;

import com.google.inject.Guice;
import com.google.inject.Injector;


public class TestServlet extends MvcDispatcherServlet {
	
	public TestServlet(Class<?>[] controllers, MvcModule module) {
		super(controllers, Guice.createInjector(module) );
	}

	public TestServlet(Class<?> controllerClass, Injector injector) {
		super(controllerClass, injector);
	}
	
	public TestServlet(Class<?> controllerClass, MvcModule module) {
		super(controllerClass, Guice.createInjector(module));
	}

}
