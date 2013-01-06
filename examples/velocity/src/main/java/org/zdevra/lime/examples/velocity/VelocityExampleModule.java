package org.zdevra.lime.examples.velocity;

import org.zdevra.guice.mvc.MvcModule;
import org.zdevra.guice.mvc.velocity.VelocityModule;

public class VelocityExampleModule extends MvcModule {

    @Override
    protected void configureControllers() {
        install(new VelocityModule(getServletContext()));
        control("/*").withController(VelocityExampleController.class);
    }
}
