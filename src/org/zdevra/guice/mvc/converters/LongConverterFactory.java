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
 * The factory creates a converter which converts long values
 */
public class LongConverterFactory implements ConverterFactory {

// ------------------------------------------------------------------------
	
	private final static Converter<Long>    typeConverter     = new LongConverter();
	private final static Converter<Long[]>  objArrayConverter = new LongObjArrayConverter();
	private final static Converter<long[]>  arrayConverter    = new LongArrayConverter();

// ------------------------------------------------------------------------

	@Override
	public Converter<?> createConvertor(Class<?> type, Annotation[] annotations) {
		if ((type == Long.class) || (type == long.class)) {
			return typeConverter;
		} else if (type == Long[].class) {
			return objArrayConverter;
		} else if (type == long[].class) {
			return arrayConverter;
		}
		
		return null;
	}
	
// ------------------------------------------------------------------------
	
	private static class LongConverter extends TypeConverter<Long> {
		@Override
		protected Long convertType(String stringValue) {
			return Long.parseLong(stringValue);
		}		
	}
	
	private static class LongArrayConverter implements Converter<long[]> {

		@Override
		public long[] convert(String name, Map<String, String[]> data) {
			String[] values = data.get(name);
			if (values == null) {
				return new long[] {};
			}
			
			long[] out = new long[values.length];
			for (int i = 0; i < values.length; ++i) {
				out[i] = Long.parseLong(values[i]);
			}
			return out;
		}		
	}
	
	private static class LongObjArrayConverter implements Converter<Long[]> {

		@Override
		public Long[] convert(String name, Map<String, String[]> data) {
			String[] values = data.get(name);
			if (values == null) {
				return new Long[] {};
			}
			
			Long[] out = new Long[values.length];
			for (int i = 0; i < values.length; ++i) {
				out[i] = Long.parseLong(values[i]);
			}
			return out;
		}		
	}
	
// ------------------------------------------------------------------------

}
