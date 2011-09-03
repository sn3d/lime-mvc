package org.zdevra.guice.mvc.converters;

import java.lang.reflect.Array;
import java.util.Map;

import org.zdevra.guice.mvc.ConversionService.Converter;

public abstract class TypeConverter<T> implements Converter {
	
// ------------------------------------------------------------------------
	
	private final Class<?> typeClass;
	
// ------------------------------------------------------------------------
	
	protected abstract T convertType(String stringValue);	
	
	
	/**
	 * Constructor
	 * @param clazz
	 */
	public TypeConverter(Class<?> clazz) {
		this.typeClass = clazz;
	}
	
// ------------------------------------------------------------------------

	@Override
	public final Object convert(String name, Map<String, String[]> data) {
		String[] values = data.get(name);
		if ((values != null) && (values.length > 0)) {
			return convertType(values[0]);
		} else {
			return convertType("");
		}
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public final Object convertArray(String name, Map<String, String[]> data) {		
		String[] values = data.get(name);		
		if ((values == null) || (values.length == 0)) {
			return Array.newInstance(typeClass, 0);
		}
		
		T[] array = (T[])Array.newInstance(typeClass, values.length);
		for (int i = 0; i < values.length; ++i) {
			array[i] = convertType(values[i]);
		}
		
		return array;
	}
	
// ------------------------------------------------------------------------

}
