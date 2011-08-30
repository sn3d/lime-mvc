package org.zdevra.guice.mvc.case7;

import com.google.inject.AbstractModule;

public class SomeModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ServiceA.class).to(ServiceAImpl.class);		
	}

}
