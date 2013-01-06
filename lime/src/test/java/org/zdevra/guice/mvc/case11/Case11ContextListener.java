package org.zdevra.guice.mvc.case11;

import org.zdevra.guice.mvc.MvcModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class Case11ContextListener extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() 
	{
		return Guice.createInjector(new MvcModule() {
			@Override
			protected void configureControllers() 
			{
				control("/case11/*")
					.withController(Case11Controller.class);
			}			
		});
	}

}
