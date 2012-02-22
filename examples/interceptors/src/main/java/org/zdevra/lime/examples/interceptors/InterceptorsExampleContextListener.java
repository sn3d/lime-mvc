package org.zdevra.lime.examples.interceptors;

import org.zdevra.guice.mvc.MvcModule;
import org.zdevra.guice.mvc.freemarker.FreemarkerModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class InterceptorsExampleContextListener extends GuiceServletContextListener 
{
	@Override
	protected Injector getInjector() 
	{
		return Guice.createInjector(
			new MvcModule() {									
				@Override
				protected void configureControllers() 
				{
					install(new FreemarkerModule(getServletContext()));
					
					//register global log interceptor for all controllers
					registerGlobalInterceptor(LogInterceptor.class);
						
					control("/public/*")
						.withController(PublicController.class);

					//register security interceptor for concrete controller						
					control("/secured/*")						
						.withController(SecuredController.class)
						.interceptor(SecurityInterceptor.class);
																		
				}
			}
		);
	}

}
