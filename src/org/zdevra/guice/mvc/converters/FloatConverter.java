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
 * The class is converting a string value to the float number.
 */
public class FloatConverter extends TypeConverter<Float> {
	
/*---------------------------- constructors ----------------------------*/
	
	/**
	 * Factory for {@link FloatConverter}
	 */
	public static class Factory implements ConverterFactory {
		
		private final Converter<?> floatConverter;
		
		public Factory() {
			this.floatConverter = new FloatConverter();
		}

		@Override
		public Converter<?> createConvertor(Class<?> type, Annotation[] annotations) {
			if ((type == Float.class) || (type == float.class)) {
				return floatConverter;
			} else {
				return null;
			}			
		}
	}
	
	
	/**
	 * private constructor
	 */
	private FloatConverter() {
		super();
	}

	
/*------------------------------- methods ------------------------------*/
	
	@Override
	protected Float convertType(String stringValue) 
	{
		try {
			return Float.parseFloat(stringValue);
		} catch (Exception e) {
			throw new IllegalConversionException("A conversion from the '" + stringValue + "' to the float failed");
		}
	}
	
	
/*----------------------------------------------------------------------*/
}
