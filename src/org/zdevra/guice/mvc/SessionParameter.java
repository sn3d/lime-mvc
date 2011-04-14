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
 * This method's parameter annotation pick up the value from
 * session.
 * <p>
 * 
 * example of picking up of the value for the 'user' session param:
 * <pre><code>
 * @@Controller
 * class MyController {
 *    @@RequestMapping("/department");
 *    public String handleRequest( @@SessionParameter("user") String user ) {
 *    	return "user is:" + user;
 *    }
 * }
 * </code></pre> 
 */
@Retention(RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
public @interface SessionParameter {
	String value();
}
