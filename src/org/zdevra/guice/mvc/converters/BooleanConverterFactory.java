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
import org.zdevra.guice.mvc.Utils;

/**
 * The factory create converter which converts boolean values
 */
public class BooleanConverterFactory implements ConverterFactory {
	
// ------------------------------------------------------------------------
	
	@Override
	public Converter<?> createConverter(Class<?> type, Annotation[] annotations)
	{		
		if ((type == boolean.class) || (type == Boolean.class)) {
			return new BooleanConverter(Utils.getAnnotation(BooleanConv.class, annotations));
		} else if (type == boolean[].class) {
			return new BooleanArrayConverter(Utils.getAnnotation(BooleanConv.class, annotations));
		} else if (type == Boolean[].class) {
			return new BooleanObjArrayConverter(Utils.getAnnotation(BooleanConv.class, annotations));
		}
		 
		return null;
	}
		
// ------------------------------------------------------------------------

	/**
	 * The class provides conversion of string to boolean object
	 */
	private static class BooleanConverter extends TypeConverter<Boolean> 
	{		
		private final BooleanConv annotation;
		
		BooleanConverter(BooleanConv annotation) {
			this.annotation = annotation;
		}
		
		@Override
		protected Boolean convertType(String stringValue) {
			return convertBoolean(stringValue, annotation);
		}
	}	
	
	
	/**
	 * The class provides conversion of string array to boolean (object) array
	 */	
	private static class BooleanObjArrayConverter implements Converter<Boolean[]> 
	{		
		private final BooleanConv annotation;
		
		BooleanObjArrayConverter(BooleanConv annotation) {
			this.annotation = annotation;
		}

		@Override
		public Boolean[] convert(String name, Map<String, String[]> data) {
			String[] values = data.get(name);
			if (values == null) {
				return new Boolean[] {};
			}			
			
			Boolean[] out = new Boolean[values.length];
			for (int i = 0; i < values.length; ++i) {
				out[i] = convertBoolean(values[i], annotation);
			}
			
			return out;
		}		
	}
	
	
	/**
	 * The class provides conversion of string array to boolean (object) array
	 */	
	private static class BooleanArrayConverter implements Converter<boolean[]> 
	{		
		private final BooleanConv annotation;
		
		BooleanArrayConverter(BooleanConv annotation) {
			this.annotation = annotation;
		}

		@Override
		public boolean[] convert(String name, Map<String, String[]> data) {
			String[] values = data.get(name);
			if (values == null) {
				return new boolean[] {};
			}			
			
			boolean[] out = new boolean[values.length];
			for (int i = 0; i < values.length; ++i) {
				out[i] = convertBoolean(values[i], annotation);
			}
			
			return out;
		}		
	}
	
// ------------------------------------------------------------------------
	
	
	/**
	 * This is acutally the main conversion method
	 */
	private static boolean convertBoolean(String stringValue, BooleanConv annotation) 
	{
		if (annotation != null) {
			if ( annotation.trueVal().equalsIgnoreCase(stringValue) ) {
				return true;
			} else if ( annotation.falseVal().equalsIgnoreCase(stringValue) ) {
				return false;
			} else {
				return false;
			}
		}
		return Boolean.parseBoolean(stringValue);		
	}

// ------------------------------------------------------------------------

}
