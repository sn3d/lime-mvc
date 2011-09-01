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
package org.zdevra.guice.mvc.converters;

import java.lang.annotation.Annotation;

import org.zdevra.guice.mvc.ConversionService.Converter;
import org.zdevra.guice.mvc.ConversionService.ConverterFactory;

/**
 * Class provide no-conversion if in your controller's method is string. 
 */
public class StringConverter implements Converter {

/*----------------------------------------------------------------------*/
	
	/**
	 * Factory class for {@link StringConverter}
	 */
	public static class Factory implements ConverterFactory {
		
		private Converter stringConverter;

		@Override
		public Converter createConvertor(Class<?> type, Annotation[] annotations) {
			if (type == String.class) {
				if (stringConverter == null) {
					stringConverter = new StringConverter();
				}
				return stringConverter;
			} 
			return null;
		}
		
	}

/*---------------------------- constructors ----------------------------*/	
	
	/**
	 * Constructor
	 */
	private StringConverter() {		
	}
	
/*------------------------------- methods ------------------------------*/
	
	@Override
	public Object convert(String stringValue) {
		return stringValue;
	}

	@Override
	public Object convert(String[] stringArray) {
		return stringArray;
	}

/*----------------------------------------------------------------------*/
}
