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
 * This annotation is deprecated and has been replaced 
 * by {@link Path} and {@link ModelName}.
 * 
 * This annotation map a HTTP request to concrete method.
 * <p>
 * 
 * In mapping, there are allowed regular expressions. You may use
 * a regexp groups and {@link UriParameter} for extraction of data 
 * from URL.
 * <p>
 * 
 * example:
 * <pre class="prettyprint">
 * {@literal @}Controller
 * class MyController {
 *    {@literal @}RequestMapping(path="/department/(.*)");
 *    public void handleRequest(@UriParameter(1) String departmentId) {
 *    ...
 *    }
 * }
 * <pre>
 * 
 * @see Path
 * @see ModelName
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Deprecated
public @interface RequestMapping {
	public String path();
	public HttpMethodType requestType() default HttpMethodType.ALL;
	public String toView() default "";
	public String nameOfResult() default "";
}
