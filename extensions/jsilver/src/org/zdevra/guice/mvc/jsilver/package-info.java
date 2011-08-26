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
 * The package implements JSilver (http://code.google.com/p/jsilver/) as a 
 * template engine responsible for views rendering. JSilver is a pure-Java 
 * implementation of Clearsilver.
 * <br>
 * JSilver operates with own data model. For this reason it's necessary transform
 * Lime model to the JSilver data. This is provided by Model Service and model conerters
 * which implements the {@link ModelConverter} interface.
 * 
 * <h3>Usage</h3>
 * Let's assume that we've got controller which selects view with name 'someview'.
 * For rendering of the HTML, we will use a JSilver template 'somehtml.jsilver'. 
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
 *    protected void configureControllers(JSilver jSilver) throws Exception
 *    {
 *       control("/*").withController(MyController.class);
 *       
 *       //setup views
 *       install(new JSilverModule(getSerlvetContext()));
 *       bindViewName("someview").toViewInstance(new JSilverView("somehtml.jsilver"));
 *    }
 * }
 * </pre>
 * 
 * There is also another way how to use JSilver view directly in controller.
 * <p>
 * 
 * <br><b>example:</b><p>
 * <pre class="prettyprint">
 * {@literal @}Controller
 * {@literal @}ToJSilverView("somehtml.jsilver")
 * public class MyController {
 *    ...
 * }
 * </pre>
 * 
 * @see org.zdevra.guice.mvc.jsilver.JSilverModule
 * @see org.zdevra.guice.mvc.jsilver.ToJSilverView
 */
package org.zdevra.guice.mvc.jsilver;
