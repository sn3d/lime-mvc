package org.zdevra.guice.mvc.case9;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class Case9ContextListener extends GuiceServletContextListener 
{
	@Override
	protected Injector getInjector() 
	{
		return Guice.createInjector(new Case9Module());
	}
}
