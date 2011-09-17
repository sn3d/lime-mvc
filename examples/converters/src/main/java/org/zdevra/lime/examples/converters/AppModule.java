package org.zdevra.lime.examples.converters;

import org.zdevra.guice.mvc.MvcConverterModule;
import org.zdevra.guice.mvc.MvcModule;
import org.zdevra.guice.mvc.freemarker.FreemarkerModule;
import org.zdevra.guice.mvc.freemarker.FreemarkerView;

public class AppModule extends MvcModule {

    @Override
    protected void configureControllers()
    {
        install(new FreemarkerModule(getServletContext()));
        control("/*").withController(AppController.class);
        registerConverter(new PersonConverterFactory());
        bindViewName("main").toViewInstance(new FreemarkerView("main.ftl"));
    }
}
