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
 * The class is converting a string value to the float number.
 */
public class FloatConvertor extends ArrayConvertor<Float> implements Convertor {
	
/*---------------------------- m. variables ----------------------------*/
	
	private final Class<?> type;
	
/*---------------------------- constructors ----------------------------*/
	
	/**
	 * Factory for {@link FloatConvertor}
	 */
	public static class Factory implements ConvertorFactory {
		
		private final Convertor floatConvertor;
		private final Convertor floatObjConvertor;
		
		public Factory() {
			this.floatConvertor = new FloatConvertor(float.class);
			this.floatObjConvertor = new FloatConvertor(Float.class);
		}

		@Override
		public Convertor createConvertor(Class<?> type, Annotation[] annotations) {
			if (type == Float.class) {
				return floatObjConvertor;
			} else if (type == long.class) {
				return floatConvertor;
			} else {
				return null;
			}			
		}
	}
	
	
	/**
	 * private constructor
	 */
	private FloatConvertor(Class<?> type) {
		this.type = type;
	}
	
/*------------------------------- methods ------------------------------*/
	
	@Override
	public Object convert(String stringValue) {
		try {
			return Float.parseFloat(stringValue);
		} catch (Exception e) {
			throw new IllegalConversionException("A conversion from the '" + stringValue + "' to the float failed");
		}
	}
	
	@Override
	public Object convert(String[] stringArray) {
		return convertArray(stringArray, this, type);
	}
	
/*----------------------------------------------------------------------*/
}
