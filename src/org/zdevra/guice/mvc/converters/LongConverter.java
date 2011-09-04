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
import org.zdevra.guice.mvc.exceptions.IllegalConversionException;

/**
 * The implemetation is converting string to long number
 *
 */
public class LongConverter extends TypeConverter<Long> {
	
/*---------------------------- constructors ----------------------------*/
	
	/**
	 * Factory class for {@link LongConverter}
	 */
	public static class Factory implements ConverterFactory {
		
		private final Converter<?> longConverter;
		
		public Factory() {
			longConverter = new LongConverter();
		}

		@Override
		public Converter<?> createConvertor(Class<?> type, Annotation[] annotations) {
			if ((type == long.class) || (type == Long.class)) {
				return longConverter;
			} else {
				return null;
			}
		}	
	}
	
	/**
	 * Private constructor. Object is created throught a Factory object.
	 */
	private LongConverter() {
		super();
	}
	
/*------------------------------- methods ------------------------------*/

	@Override
	protected Long convertType(String stringValue) {
		try {
			return Long.parseLong(stringValue);
		} catch (Exception e) {
			throw new IllegalConversionException("A conversion from the '" + stringValue + "' to the long failed");
		}
	}
	
/*----------------------------------------------------------------------*/	

}
