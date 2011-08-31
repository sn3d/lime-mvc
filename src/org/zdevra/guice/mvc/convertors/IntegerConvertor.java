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
package org.zdevra.guice.mvc.convertors;

import java.lang.annotation.Annotation;

import org.zdevra.guice.mvc.ConversionService.Convertor;
import org.zdevra.guice.mvc.ConversionService.ConvertorFactory;
import org.zdevra.guice.mvc.exceptions.IllegalConversionException;

/**
 * The convertor's implementation which is  responsible 
 * for converting a string values to an integers.
 */
public class IntegerConvertor extends ArrayConvertor<Integer> implements Convertor {
	
/*---------------------------- m. variables ----------------------------*/
	
	private final Class<?> type;
	
/*---------------------------- constructors ----------------------------*/
	
	/**
	 * The factory class for {@link IntegerConvertor}
	 */
	public static class Factory implements ConvertorFactory {
		
		private final Convertor integerConvertor;
		private final Convertor intConvertor;
		
		public Factory() {
			this.integerConvertor = new IntegerConvertor(Integer.class);
			this.intConvertor = new IntegerConvertor(int.class);
		}
				
		@Override
		public Convertor createConvertor(Class<?> type, Annotation[] annotations) {
			if (type == Integer.class){
				return integerConvertor;
			} else if (type == int.class) {
				return intConvertor;
			} else {
				return null;
			}
						
		}
	}
	
	
	/**
	 * Private constructor. This class is constructed via
	 * inner Factory class
	 */
	private IntegerConvertor(Class<?> type) {
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
