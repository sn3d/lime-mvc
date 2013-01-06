/**
 * ***************************************************************************
 * Copyright 2012 Zdenko Vrabel
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
 * This annotation determines the method's argument will be filled-in with value
 * from request's attributes.
 *
 * Let's assume we've got security interceptor which setup the 'USER' request
 * attribute.
 *
 * <pre class="prettyprint"> User actualUser = ...; request.setAttribute("USER",
 * actualUser);
 * </pre>
 *
 * In our controller, we can fill-in the 'USER' value into method's attribute
 * easily:
 *
 * <pre class="prettyprint"> {@literal @}Controller class MyController {
 * {@literal @}Path("/department"); public String handleRequest(
 * {@literal @}RequestScopedAttribute("USER") User actualUser ) { return "user
 * is:" + user; } }
 * </pre>
 *
 */
@Retention(RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface RequestScopedAttribute {

    String value();
}
