/*****************************************************************************
 * Copyright 2012 Zdenko Vrabel
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

/**
 * The annotation determines that the view should redirect to some URL.
 * Model data will be used as a URL paramteres. The annotation's value 
 * can be absolute URL in form 'http://someserver:port/path' or also can
 * be relative path in form '/somepath'. Relative path can be relative 
 * onto controller context when contextRelative is set to true  or onto server
 * when contextRelative is set to false. 
 * <p>
 * Model data are transformed as URL parameters. Let's assume controller's method:
 * 
 * <pre class="prettyprint">
 * {@literal @}Path("/somepath")
 * {@literal @}Model("param")
 * {@literal @}RedirectUrl("http://server/path")
 * public String redirectToSomewhere()
 * {
 *    return "value";
 * }
 * </pre>
 * 
 * The redirection URL will looks like 'http://server/path?param=value'.
 * 
 * @see org.zdevra.guice.mvc.views.RedirectViewPoint
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface RedirectView {
	public String value();	
	boolean contextRelative() default true;
}
