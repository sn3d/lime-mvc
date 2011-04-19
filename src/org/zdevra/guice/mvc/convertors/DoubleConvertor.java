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
 * The class converts a string value to the double number.
 */
public class DoubleConvertor extends ArrayConvertor<Double> implements Convertor {
	
/*---------------------------- m. variables ----------------------------*/
	
	private final Class<?> type;
	
/*---------------------------- constructors ----------------------------*/
	
	/**
	 * Factory object for double convertor
	 */
	public static class Factory implements ConvertorFactory {
		
		private final Convertor doubleConvertor;
		private final Convertor doubleObjConvertor;
		
		public Factory() {
			this.doubleConvertor = new DoubleConvertor(double.class);
			this.doubleObjConvertor = new DoubleConvertor(Double.class);
		}

		@Override
		public Convertor createConvertor(Class<?> type, Annotation[] annotations) {
			if (type == double.class) {
				return doubleConvertor;
			} else if (type == Double.class) {
				return doubleObjConvertor;
			} else {
				throw new IllegalStateException("the type is not a double type");
			}
		}		
	}
	
	
	/**
	 * The hidden private constructor. The object is constructed throught the factory object 
	 */
	private DoubleConvertor(Class<?> type) {
		this.type = type;
	}

	
/*------------------------------- methods ------------------------------*/
	
	@Override
	public Object convert(String stringValue) {
		try {
			return Double.parseDouble(stringValue);
		} catch (Exception e) {
			throw new IllegalConversionException("A conversion from the '" + stringValue + "' to the double failed");
		}
	}


	@Override
	public Object convert(String[] stringArray) {
		return convertArray(stringArray, this, type);
	}
	
/*----------------------------------------------------------------------*/

}