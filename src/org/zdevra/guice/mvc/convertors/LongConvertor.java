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
 * The implemetation is converting string to long number
 *
 */
public class LongConvertor extends ArrayConvertor<Long> implements Convertor {
	
/*---------------------------- m. variables ----------------------------*/
	
	private final Class<?> type;
	
/*---------------------------- constructors ----------------------------*/
	
	/**
	 * Factory class for {@link LongConvertor}
	 */
	public static class Factory implements ConvertorFactory {
		
		private final Convertor longConvertor;
		private final Convertor longObjConvertor;
		
		public Factory() {
			longConvertor = new LongConvertor(long.class);
			longObjConvertor = new LongConvertor(Long.class);
		}

		@Override
		public Convertor createConvertor(Class<?> type, Annotation[] annotations) {
			if (type == long.class) {
				return longConvertor;
			} else if (type == Long.class) {
				return longObjConvertor;
			} else {
				throw new IllegalStateException("the type is not a long type");
			}
		}
	
	}
	
	/**
	 * Private constructor. Object is created throught a Factory object.
	 */
	private LongConvertor(Class<?> type) {
		this.type = type;
	}
	
/*------------------------------- methods ------------------------------*/
	
	@Override
	public Object convert(String stringValue) {
		try {
			return Long.parseLong(stringValue);
		} catch (Exception e) {
			throw new IllegalConversionException("A conversion from the '" + stringValue + "' to the long failed");
		}
	}

	@Override
	public Object convert(String[] stringArray) {
		return convertArray(stringArray, this, type);
	}
	
/*----------------------------------------------------------------------*/	

}
