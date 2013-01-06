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
package org.zdevra.guice.mvc.annotations;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The annontation tell to converter how to convert a string to boolean.
 *
 * Let's assume that we don't want convert regular 'true' 'false' strings, but
 * we want convert Y like true a N like false:
 *
 * <pre class="prettyprint"> {@literal @}Controller public class MyController {
 *
 * {@literal @}Path("/method") public void doSomething(
 * {@literal @}RequestParameter("bool-param")
 * {@literal @}BooleanConv(trueVal="Y", falseVal="N") boolean param) {
 *
 * }
 * }
 * </pre>
 *
 *
 * @see BooleanConverter
 */
@Retention(RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface BooleanConv {

    public String trueVal() default "true";

    public String falseVal() default "false";
}
