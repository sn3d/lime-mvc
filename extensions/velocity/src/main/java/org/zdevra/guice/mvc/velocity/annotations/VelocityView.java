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
package org.zdevra.guice.mvc.velocity.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used in controller and is
 * telling to Lime MVC that we want to render a 
 * produced data via Velocity's template.
 * <br>
 * <b>example:</b>
 * <pre class="prettyprint">
 * {@literal @}Controller
 * public class MyController {
 *    
 *    {@literal @}Path("/helloworld")
 *    {@literal @}VelocityView("view.velocity")
 *    public String helloWorld() {
 *       ...
 *    }
 * }
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface VelocityView {
	public String value();
}

