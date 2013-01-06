/**
 * ***************************************************************************
 * Copyright 2011 Zdenko Vrabel
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 ****************************************************************************
 */
package org.zdevra.guice.mvc.case4;

import org.zdevra.guice.mvc.MvcModule;
import org.zdevra.guice.mvc.TestView;

public class Case4Module extends MvcModule {

    @Override
    protected void configureControllers() {
        bindException(CustomException.class).toHandler(CustomExceptionHandler.class);
        bindException(AdvancedHandledException.class).toHandlerInstance(new AdvancedHandler());
        bindException(ExceptionForErrorPage.class).toErrorView("errorView");
        bindViewName("default").toViewInstance(new TestView("0"));
        bindViewName("errorView").toViewInstance(new TestView("errorpage"));
    }
}
