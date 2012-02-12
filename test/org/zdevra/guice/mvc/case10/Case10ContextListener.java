package org.zdevra.guice.mvc.case10;

import org.zdevra.guice.mvc.MvcModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class Case10ContextListener extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() 
	{
		return Guice.createInjector(new MvcModule() {

			@Override
			protected void configureControllers() 
			{
				registerGlobalInterceptor(SecurityInterceptor.class);
				
				control("/case10/*")
				.withController(Case10Controller.class);				

				
				control("/case10log/*")
					.withController(Case10Controller.class)
					.interceptor(LogInterceptor.class);

				
				control("/case10after/*")
					.withController(Case10Controller.class);				
				
			}			
		});
	}
}
