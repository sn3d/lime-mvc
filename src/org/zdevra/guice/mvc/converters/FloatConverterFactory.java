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
 * The factory creates a converter which provide conversion of float values
 */
public class FloatConverterFactory implements ConverterFactory {
	
// ------------------------------------------------------------------------
	
	private final static Converter<Float>    floatConverter = new FloatConverter();
	private final static Converter<Float[]>  floatObjArrayConverter = new FloatObjArrayConverter();
	private final static Converter<float[]>  floatArrayConverter = new FloatArrayConverter();
	
// ------------------------------------------------------------------------

	@Override
	public Converter<?> createConverter(Class<?> type, Annotation[] annotations) {
		if ((type == Float.class) || (type == float.class)) {
			return floatConverter;
		} else if (type == Float[].class) {
			return floatObjArrayConverter;
		} else if (type == float[].class) {
			return floatArrayConverter;
		}
		
		return null;
	}
	
// ------------------------------------------------------------------------
	
	private static class FloatConverter extends TypeConverter<Float> {
		@Override
		protected Float convertType(String stringValue) {
			return Float.parseFloat(stringValue);
		}		
	}

	private static class FloatArrayConverter implements Converter<float[]> {
		
		@Override
		public float[] convert(String name, Map<String, String[]> data) {
			String[] values = data.get(name);
			if (values == null) {
				return new float[] {};
			}
			
			float[] out = new float[values.length];
			for (int i = 0; i < values.length; ++i) {
				out[i] = Float.parseFloat(values[i]);
			}
			return out;
		}
				
	}

	private static class FloatObjArrayConverter implements Converter<Float[]> {
		
		@Override
		public Float[] convert(String name, Map<String, String[]> data) {
			String[] values = data.get(name);
			if (values == null) {
				return new Float[] {};
			}
			
			Float[] out = new Float[values.length];
			for (int i = 0; i < values.length; ++i) {
				out[i] = Float.parseFloat(values[i]);
			}
			return out;
		}
				
	}
	
// ------------------------------------------------------------------------

}
