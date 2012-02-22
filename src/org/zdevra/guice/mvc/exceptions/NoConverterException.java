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
package org.zdevra.guice.mvc.exceptions;

import org.zdevra.guice.mvc.ConversionService;

/**
 * Lime MVC is not able convert the String value from HTTP request to the
 * controller's method's argument type. Check the method's arguments or 
 * define new converter for the missing type.
 *
 */
public class NoConverterException extends MvcException {
	public NoConverterException(Class<?> type) {
		super("No converter for String -> " + type.getCanonicalName() + " is defined.");
	}

    public NoConverterException(Class<? extends ConversionService.ConverterFactory> factory, Class<?> type) {
        super("Invalid converter factory '" + factory.getName() + "' for String -> " + type.getCanonicalName() + " is defined.");
    }
}
