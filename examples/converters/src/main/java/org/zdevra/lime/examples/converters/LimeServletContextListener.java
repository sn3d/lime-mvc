package org.zdevra.lime.examples.converters;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class LimeServletContextListener extends GuiceServletContextListener {
	@Override
	protected Injector getInjector() {
        return Guice.createInjector(new AppModule());
	}
}
