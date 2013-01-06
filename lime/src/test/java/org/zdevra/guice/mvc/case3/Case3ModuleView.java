package org.zdevra.guice.mvc.case3;

import org.zdevra.guice.mvc.TestView;
import org.zdevra.guice.mvc.ViewModule;

public class Case3ModuleView extends ViewModule {

    @Override
    protected void configureViews() {
        bindViewName("five").toViewInstance(new TestView("5"));
    }
}
