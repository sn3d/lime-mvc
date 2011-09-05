package org.zdevra.guice.mvc.converters;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.zdevra.guice.mvc.ConversionService.Converter;
import org.zdevra.guice.mvc.ConversionService.ConverterFactory;

public class DoubleConverterFactory implements ConverterFactory {
	
// ------------------------------------------------------------------------
	
	private final static Converter<Double>    typeConverter     = new DoubleConverter();
	private final static Converter<Double[]>  objArrayConverter = new DoubleObjArrayConverter();
	private final static Converter<double[]>  arrayConverter    = new DoubleArrayConverter();

// ------------------------------------------------------------------------

	@Override
	public Converter<?> createConvertor(Class<?> type, Annotation[] annotations) 
	{
		if ((type == Double.class) || (type == double.class)) {
			return typeConverter;
		} else if (type == Double[].class) {
			return objArrayConverter;
		} else if (type == double[].class) {
			return arrayConverter;
		}
		
		return null;
	}
	
// ------------------------------------------------------------------------
	
	
	private static class DoubleConverter extends TypeConverter<Double> {
		@Override
		protected Double convertType(String stringValue) {
			return Double.parseDouble(stringValue);
		}		
	}
		
	private static class DoubleArrayConverter implements Converter<double[]> {

		@Override
		public double[] convert(String name, Map<String, String[]> data) {
			String[] values = data.get(name);
			if (values == null) {
				return new double[] {};
			}
			
			double[] out = new double[values.length];
			for (int i = 0; i < values.length; ++i) {
				out[i] = Double.parseDouble(values[i]);
			}
			return out;
		}		
	}
	
	private static class DoubleObjArrayConverter implements Converter<Double[]> {

		@Override
		public Double[] convert(String name, Map<String, String[]> data) {
			String[] values = data.get(name);
			if (values == null) {
				return new Double[] {};
			}
			
			Double[] out = new Double[values.length];
			for (int i = 0; i < values.length; ++i) {
				out[i] = Double.parseDouble(values[i]);
			}
			return out;
		}		
	}
	
// ------------------------------------------------------------------------

}
