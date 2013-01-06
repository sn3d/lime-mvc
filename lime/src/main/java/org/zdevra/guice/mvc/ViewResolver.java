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
package org.zdevra.guice.mvc;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Used for resolving of a view after request was processed by a controller
 * class.
 *
 * Concrete implementation is registered in MvcModule's configuration. If no
 * concrete implementation is registered, then is used default resolver
 * {@link DefaultViewResolver}.
 *
 * A example of how to regiter a customized resolver: <pre style="prettyprint">
 * public class MyModule extends MvcModule { protected void
 * configureControllers() {
 * bind(ViewResolver.class).to(CustomViewResolver.class); } }
 * </pre>
 *
 * Keep your implementation of the ViewResolver stateless, immutable and fast.
 *
 * @see DefaultViewResolver
 * @see MvcModule
 */
public interface ViewResolver {

    /**
     * Called when request is forwarded from controller class to view.
     *
     * Keep the implementation small and fast. It's part of code which is called
     * often.
     */
    public void resolve(ViewPoint view, ModelMap model, HttpServlet servlet, HttpServletRequest req, HttpServletResponse resp);
}
