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
 * The class converts a string value to the double number.
 */
public class DoubleConverter extends TypeConverter<Double> {
	
/*---------------------------- constructors ----------------------------*/
	
	/**
	 * Factory class for {@link DoubleConverter}
	 */
	public static class Factory implements ConverterFactory {
		
		private final Converter doubleConverter;
		
		public Factory() {
			this.doubleConverter = new DoubleConverter();
		}

		@Override
		public Converter createConvertor(Class<?> type, Annotation[] annotations) {
			if ((type == double.class) || (type == Double.class)) {
				return doubleConverter;
			} else {
				return null;
			}
		}		
	}
	
	
	/**
	 * The hidden private constructor. The object is constructed throught the factory object 
	 */
	private DoubleConverter() {
		super(Double.class);
	}

	
/*------------------------------- methods ------------------------------*/

	@Override
	protected Double convertType(String stringValue) {
		try {
			return Double.parseDouble(stringValue);
		} catch (Exception e) {
			throw new IllegalConversionException("A conversion from the '" + stringValue + "' to the double failed");
		}
	}

	
/*----------------------------------------------------------------------*/

}
