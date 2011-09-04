package org.zdevra.guice.mvc.converters;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.zdevra.guice.mvc.ConversionService.Converter;
import org.zdevra.guice.mvc.ConversionService.ConverterFactory;

public class IntegerConverterFactory implements ConverterFactory {
// ------------------------------------------------------------------------	

	private final static Converter<Integer>   integerConverter = new IntegerConverter();
	private final static Converter<Integer[]> integerArrayConverter = new IntegerArrayConverter();
	private final static Converter<int[]>     intArrayConverter = new IntArrayConverter();

// ------------------------------------------------------------------------
			
	@Override
	public Converter<?> createConvertor(Class<?> type, Annotation[] annotations) 
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
