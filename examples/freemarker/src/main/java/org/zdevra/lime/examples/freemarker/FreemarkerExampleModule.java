package org.zdevra.lime.examples.freemarker;

import org.zdevra.guice.mvc.MvcModule;
import org.zdevra.guice.mvc.freemarker.FreemarkerModule;

public class FreemarkerExampleModule extends MvcModule {
	@Override
	protected void configureControllers() {
		install(new FreemarkerModule(getServletContext()));
		control("/*").withController(FreemarkerExampleController.class);
	}
}
