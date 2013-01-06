package org.zdevra.lime.examples.converters;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import org.zdevra.guice.mvc.MvcModule;
import org.zdevra.guice.mvc.freemarker.FreemarkerModule;
import org.zdevra.guice.mvc.freemarker.FreemarkerViewPoint;

/**
 * Context listener prepare a Guice Injector and application's MVC module
 */
public class LimeServletContextListener extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new MvcModule() {
            @Override
            protected void configureControllers() {

                //setup converter for Person
                registerConverter(new PersonConverterFactory());

                //setup controller
                control("/*").withController(AppController.class);

                //setup views
                install(new FreemarkerModule(getServletContext()));
                bindViewName("main").toViewInstance(new FreemarkerViewPoint("main.ftl"));
            }
        });
    }
}
