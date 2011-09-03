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
 * The convertor's implementation which is  responsible 
 * for converting a string values to an integers.
 */
public class IntegerConverter extends TypeConverter<Integer> {
	
/*---------------------------- constructors ----------------------------*/
	
	/**
	 * The factory class for {@link IntegerConverter}
	 */
	public static class Factory implements ConverterFactory {
		
		private final Converter integerConverter;
		
		public Factory() {
			this.integerConverter = new IntegerConverter();
		}
				
		@Override
		public Converter createConvertor(Class<?> type, Annotation[] annotations) {
			if ((type == Integer.class) || (type == int.class)) {
				return integerConverter;
			} else {
				return null;
			}					
		}
	}
	
	
	/**
	 * Private constructor. This class is constructed via
	 * inner Factory class
	 */
	private IntegerConverter() {
		super(Integer.class);
	}


/*------------------------------- methods ------------------------------*/

	@Override
	protected Integer convertType(String stringValue) {
		try {
			return Integer.parseInt(stringValue);
		} catch (Exception e) {
			throw new IllegalConversionException("A conversion from the '" + stringValue + "' to the int failed");
		}
	}	
	
/*----------------------------------------------------------------------*/

}
