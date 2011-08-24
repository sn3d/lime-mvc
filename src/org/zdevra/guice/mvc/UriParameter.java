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
 * The method's parameter annotation tells the value will be picked up
 * from request's URL. Each controller's method is annotated by the {@link @RequestMapping} which defines
 * URL. In this URL you can use regexp and grous.  The annotation's value is the group's index. 
 * 
 * <p>example:
 * <pre class="prettyprint">
 * {@literal @}Path("/controller/([a..z]+)/([0..9]+)")
 * public void controllerMethod({@literal @}UriParameter(1) String param1, {@literal @}UriParameter(2) param2) {
 *    //param1 == value in group [a..z]+;
 *    //param2 == value in group [0..9]+; 
 * ...
 * }
 * </pre>
 * 
 * @see org.zdevra.guice.mvc.parameters.UriParam
 */
@Retention(RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
public @interface UriParameter {
	int value();
}
