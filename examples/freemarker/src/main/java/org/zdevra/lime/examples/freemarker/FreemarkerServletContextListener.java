package org.zdevra.lime.examples.freemarker;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class FreemarkerServletContextListener extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new FreemarkerExampleModule());
	}

}
