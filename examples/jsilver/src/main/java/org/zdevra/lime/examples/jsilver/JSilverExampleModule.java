package org.zdevra.lime.examples.jsilver;

import org.zdevra.guice.mvc.MvcModule;
import org.zdevra.guice.mvc.jsilver.JSilverModule;

public class JSilverExampleModule extends MvcModule {

    @Override
    protected void configureControllers() {
        install(new JSilverModule(getServletContext()));
        control("/*").withController(JSilverExampleController.class);
    }
}
