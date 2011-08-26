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

/**
 * The package implements into Lime MVC a Velocity as a 
 * template engine responsible for views rendering.   
 * <p>
 * 
 * <h3>Usage</h3>
 * Let's assume that we've got controller which selects view with name 'someview'.
 * For rendering of the HTML, we will use a Velocity template 'somehtml.velocity'. 
 * <p>
 * 
 * <br><b>controller:</b></p>
 * <pre class="prettyprint">
 * {@literal @}Controller
 * {@literal @}ToView("someview")
 * public class MyController {
 *    ...
 *    {@literal @}Path("/helloworld") {@literal @}ModelName("msg")
 *    public String showHelloWorld() {
 *       return "Hello World";
 *    }
 * }
 * </pre>
 * 
 * <br>the module and view setup will be:<p>
 * <pre class="prettyprint">
 * public class MyModule extends MvcModule {
 *    protected void configureControllers(VelocityEngine velocity)
 *    {
 *       control("/*").withController(MyController.class);
 *       
 *       // setup views       
 *       install(new VelocityModule(getServletContext()));
 *       bindViewName("someview").toViewInstance(new VelocityView("somehtml.velocity"));
 *    }
 * }
 * </pre>
 * 
 * There is also another way how to use Velocity view. Directly in the controller.
 * <p>
 * 
 * <br><b>example:</b><p>
 * <pre class="prettyprint">
 * {@literal @}Controller
 * {@literal @}ToVelocityView("somehtml.velocity")
 * public class MyController {
 *    ...
 * }
 * </pre>
 * 
 */
package org.zdevra.guice.mvc.velocity;