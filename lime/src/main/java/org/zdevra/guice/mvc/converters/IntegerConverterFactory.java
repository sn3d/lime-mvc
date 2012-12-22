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
import java.util.Map;

import org.zdevra.guice.mvc.ConversionService.Converter;
import org.zdevra.guice.mvc.ConversionService.ConverterFactory;

/**
 * The factory creates a converter which provide conversion of integer values
 */
public class IntegerConverterFactory implements ConverterFactory {
// ------------------------------------------------------------------------	

	private final static Converter<Integer>   integerConverter = new IntegerConverter();
	private final static Converter<Integer[]> integerArrayConverter = new IntegerArrayConverter();
	private final static Converter<int[]>     intArrayConverter = new IntArrayConverter();

// ------------------------------------------------------------------------
			
	@Override
	public Converter<?> createConverter(Class<?> type, Annotation[] annotations)
	{
		if ((type == Integer.class) || (type == int.class)) {
			return integerConverter;
		} else if (type == Integer[].class) {
			return integerArrayConverter;
		} else if (type == int[].class) {
			return intArrayConverter;
		}
		
		return null;
	}
	
// ------------------------------------------------------------------------
	
	private static class IntegerConverter extends TypeConverter<Integer> {
		@Override
		protected Integer convertType(String stringValue) {
			return Integer.parseInt(stringValue);
		}		
	}
	
	
	private static class IntArrayConverter implements Converter<int[]> {

		@Override
		public int[] convert(String name, Map<String, String[]> data) {
			String[] values = data.get(name);
			if (values == null) {
				return new int[] {};
			}
			
			int[] out = new int[values.length];
			for (int i = 0; i < values.length; ++i) {
				out[i] = Integer.parseInt(values[i]);
			}
			return out;
		}		
	}
		
	
	private static class IntegerArrayConverter implements Converter<Integer[]> {

		@Override
		public Integer[] convert(String name, Map<String, String[]> data) {
			String[] values = data.get(name);
			if (values == null) {
				return new Integer[] {};
			}

			Integer[] out = new Integer[values.length];
			for (int i = 0; i < values.length; ++i) {
				out[i] = Integer.parseInt(values[i]);
			}
			return out;
		}		
	}
	
// ------------------------------------------------------------------------

}
