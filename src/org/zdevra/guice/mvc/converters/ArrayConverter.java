package org.zdevra.guice.mvc.converters;

import java.lang.reflect.Array;
import java.util.Map;

import org.zdevra.guice.mvc.ConversionService.Converter;

public abstract class ArrayConverter<T> implements Converter<T[]> {
	
// ------------------------------------------------------------------------
	
	private final Class<?> clazz;
	
// ------------------------------------------------------------------------
	
	protected abstract int count(String name, Map<String, String[]> data);
	protected abstract T   convertItem(String name, Map<String, String[]> data, int iteration);
	
// ------------------------------------------------------------------------
	
	public ArrayConverter(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public final T[] convert(String name, Map<String, String[]> data) {		
		int count = count(name, data);
		T[] out = (T[]) Array.newInstance(clazz, count);
		for (int i = 0; i < count; ++i) {
			out[i] = convertItem(name, data, i);  
		}
		return out;
	}
	
// ------------------------------------------------------------------------

}
