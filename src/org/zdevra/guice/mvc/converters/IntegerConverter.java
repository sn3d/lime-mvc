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
public class IntegerConverter extends ArrayConverter<Integer> implements Converter {
	
/*---------------------------- m. variables ----------------------------*/
	
	private final Class<?> type;
	
/*---------------------------- constructors ----------------------------*/
	
	/**
	 * The factory class for {@link IntegerConverter}
	 */
	public static class Factory implements ConverterFactory {
		
		private final Converter integerConverter;
		private final Converter intConverter;
		
		public Factory() {
			this.integerConverter = new IntegerConverter(Integer.class);
			this.intConverter = new IntegerConverter(int.class);
		}
				
		@Override
		public Converter createConvertor(Class<?> type, Annotation[] annotations) {
			if (type == Integer.class){
				return integerConverter;
			} else if (type == int.class) {
				return intConverter;
			} else {
				return null;
			}
						
		}
	}
	
	
	/**
	 * Private constructor. This class is constructed via
	 * inner Factory class
	 */
	private IntegerConverter(Class<?> type) {
		this.type = type;
	}

/*------------------------------- methods ------------------------------*/

	@Override
	public Object convert(String stringValue) {
		try {
			return Integer.parseInt(stringValue);
		} catch (Exception e) {
			throw new IllegalConversionException("A conversion from the '" + stringValue + "' to the integer failed");
		}
	}

	@Override
	public Object convert(String[] stringArray) {
		return convertArray(stringArray, this, type);
	}
	
/*----------------------------------------------------------------------*/

}
