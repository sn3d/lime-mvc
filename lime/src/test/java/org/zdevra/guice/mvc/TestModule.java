package org.zdevra.guice.mvc;

import org.zdevra.guice.mvc.DefaultViewResolver;
import org.zdevra.guice.mvc.ViewScannerService;

import com.google.inject.AbstractModule;

public class TestModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ViewScannerService.class);
        bind(ViewResolver.class).to(DefaultViewResolver.class);
    }
}
