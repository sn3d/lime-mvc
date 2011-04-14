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

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This parameter annotation tell to the LIME that parameter 
 * is filled by value picked from URL request.
 * <p>
 * 
 * Let's assume request www.someserver.com/myapp/mycontroller/method?param1=value1
 * example of picking up of the param1 'value1' like a parameter of method:
 * <p>
 * 
 * <pre><code>
 * @Controller
 * class MyController {
 * 
 *    @RequestMapping(path="/method")
 *    public String method( @RequestParameter("param1") String param1 ) {
 *       return "parameter is:" + param1;
 *    } 
 * } 
 * </code></pre>
 * 
 * <p>
 * Annotation also might process an array of values from request. Let's assume
 * that we have an array of values in html:
 * <pre><code>
 * <input name="someval[1]" type="text" size="20" maxlength="40" />
 * <input name="someval[2]" type="text" size="20" maxlength="40" />
 * <input name="someval[3]" type="text" size="20" maxlength="40" />
 * </code></pre>
 * 
 * The example code for this example, which process the array, looks like:
 * <pre><code>
 * @Controller
 * class MyController {
 * 
 *    @RequestMapping(path="/method")
 *    public void method( @RequestParameter("someval") int[] param1 ) {
 *       ...
 *    } 
 * } 
 * </code></pre>
 * <
 */
@Retention(RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
public @interface RequestParameter {
	String value();
}
