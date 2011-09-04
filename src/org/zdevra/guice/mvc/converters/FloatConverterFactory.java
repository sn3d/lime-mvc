package org.zdevra.guice.mvc.converters;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.zdevra.guice.mvc.ConversionService.Converter;
import org.zdevra.guice.mvc.ConversionService.ConverterFactory;

public class FloatConverterFactory implements ConverterFactory {
	
// ------------------------------------------------------------------------
	
	private final static Converter<Float>    floatConverter = new FloatConverter();
	private final static Converter<Float[]>  floatObjArrayConverter = new FloatObjArrayConverter();
	private final static Converter<float[]>  floatArrayConverter = new FloatArrayConverter();
	
// ------------------------------------------------------------------------

	@Override
	public Converter<?> createConvertor(Class<?> type, Annotation[] annotations) {
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
