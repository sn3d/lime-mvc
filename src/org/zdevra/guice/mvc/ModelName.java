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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation define name of the method's
 * result in model. 
 * <p>
 * <br>
 * <b>example of named result:</b>
 * <pre class="prettyprint">
 * {@literal @}Controller
 * class MyController {
 *    {@literal @}Path("/department/(.*)") {@literal @}ModelName("msg")
 *    public String helloWorld() {
 *      return "Hello World";
 *    }
 * }
 * </pre>
 * 
 * In example above, the message 'Hello World' will be placed into model with
 * name 'msg'. If there is no name defined, the lime-mvc choose the method's 
 * name as a name of result in model.
 * <p>
 * <br>
 * <b>example of unnamed result:</b>
 * <pre class="prettyprint">
 * {@literal @}Controller
 * class MyController {
 *    {@literal @}Path("/department/(.*)")
 *    public String helloWorld() {
 *      return "Hello World";
 *    }
 * }
 * </pre>
 * In example above, the message will be placed into model with method's name 'helloWorld'.
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface ModelName {
	public String value();
}
