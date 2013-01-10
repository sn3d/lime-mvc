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
package org.zdevra.guice.mvc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.zdevra.guice.mvc.HttpMethodType;

/**
 * This annotation map a HTTP request to concrete method.
 * <p>
 * 
 * In mapping, there are allowed regular expressions. You may use
 * a regexp groups and {@link UriParameter} for extraction of data 
 * from URL.
 * <p><br>
 * 
 * example:
 * <pre class="prettyprint">
 * {@literal @}Controller
 * class MyController {
 *    {@literal @}Path("/department/(.*)")
 *    public void handleRequest(@UriParameter(1) String departmentId) {
 *    ...
 *    }
 * }
 * <pre>
 * 
 * You may specify also HTTP method (GET, PUT, DELETE etc). Normally the method 
 * will accept all HTTP requests with any method. If you want to specify for
 * which HTTP method it should be invoked, use the one of HttpMethod annotations.
 * <br>
 * 
 * example:
 * <pre class="prettyprint">
 * {@literal @}Controller
 * class MyController {
 *    {@literal @}PUT {@literal @}Path("/department/(.*)")
 *    public void handleRequest(@UriParameter(1) String departmentId) {
 *    ...
 *    }
 * }
 * <pre>
 *
 * @see HttpMethodType
 * @see GET
 * @see POST
 * @see PUT
 * @see DELETE
 * @see Controller
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Path {

    public String value();
}
