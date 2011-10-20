/*****************************************************************************
 * Copyright 2011 Zdenko Vrabel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *****************************************************************************/
package org.zdevra.guice.mvc;

import com.google.inject.Binder;
import javax.servlet.http.HttpServlet;
import java.util.logging.Logger;

/**
 * The class is extended version of the {@link org.zdevra.guice.mvc.ControllerDefinition} which
 * creates not regular Servlet Dispatcher but Async version of the Servlet Dispatcher.
 *
 * The definition is used when user uses the MvcModule.controlAsync() method.
 */
public class AsyncControllerDefinition extends ControllerDefinition {

// ------------------------------------------------------------------------

    private static final Logger  logger  = Logger.getLogger(AsyncControllerDefinition.class.getName());
    public  static final Factory ASYNCFACTORY = new AsyncFactory();

// ------------------------------------------------------------------------

    /**
     * Factory's implementation creates the instance of ControllerDefinition
     */
    public static class AsyncFactory extends Factory {
        public ControllerDefinition create(String urlPattern) {
            return new AsyncControllerDefinition(urlPattern);
        }
    }

// ------------------------------------------------------------------------

    /**
     * Hidden constructor. The class is instantiated via Factory
     * interface.
     */
    private AsyncControllerDefinition(String urlPattern) {
        super(urlPattern);
    }


    @Override
    public HttpServlet createServlet(Binder binder) {
        logger.info("for path '" + getUrlPattern() + "' should be registered follwing async controllers: " + this.controllers);
        return new MvcAsyncDispatcherServlet(this.controllers);
    }
}
