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

import org.zdevra.guice.mvc.converters.NoConverterFactory;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This parameter annotation tell to the LIME that method's parameter 
 * is filled up by a value, which is picked up from an URL request.
 * <p>
 * 
 * Let's assume request www.someserver.com/myapp/mycontroller/method?param1=value1
 * example of picking up of the param1 'value1' like a parameter of method:
 * <p>
 * 
 * <pre class="prettyprint">
 * {@literal @}Controller
 * class MyController {
 * 
 *    {@literal @}Path("/method")
 *    public String method( {@literal @}RequestParameter("param1") String param1 ) {
 *       return "parameter is:" + param1;
 *    } 
 * } 
 * </pre>
 * 
 * <p>
 * Annotation also might process an array of values from request. Let's assume
 * that we have an array of values in html:
 * <pre class="prettyprint">
 * <input name="someval[1]" type="text" size="20" maxlength="40" />
 * <input name="someval[2]" type="text" size="20" maxlength="40" />
 * <input name="someval[3]" type="text" size="20" maxlength="40" />
 * </pre>
 * 
 * The example code for this example, which process the array, looks like:
 * <pre class="prettyprint">
 * {@literal @}Controller
 * class MyController {
 * 
 *    {@literal @}Path("/method")
 *    public void method( {@literal @}RequestParameter("someval") int[] param1 ) {
 *       ...
 *    } 
 * } 
 * </pre>
 * <
 */
@Retention(RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
public @interface RequestParameter {
	String value();
    Class<? extends ConversionService.ConverterFactory> converterFactory() default NoConverterFactory.class;
}
