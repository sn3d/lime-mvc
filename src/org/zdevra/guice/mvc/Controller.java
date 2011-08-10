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

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * All classes annotated by this annotation are regular MVC conctrolers
 * and can be handled by a MVC Dispatcher.
 * Also this annotation tells to the LIME which attributes stored in the
 * model should be stored in the session as well.
 * <p>
 * 
 * Example of the controller with 2 attributes, they will be 
 * stored in the session.
 * <p>
 * 
 * <pre class="prettyprint">
 * {@literal @}Controller(sessionAttributes={"username", "country"})
 * class MyController() {
 *     ...
 *     {@literal @}RequestMapping(path="/home")
 *     public Model doSomething() {
 *     	   ...
 *         Model m = new Model();
 *         m.addObject("username", username);
 *         m.addObject("country",  country);
 *         return m;
 *     }
 * }
 * <code></pre>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
	public String[] sessionAttributes() default {};
	public String toView() default "";
	
}
