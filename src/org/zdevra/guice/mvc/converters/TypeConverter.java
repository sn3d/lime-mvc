package org.zdevra.guice.mvc.converters;

import java.util.Map;

import org.zdevra.guice.mvc.ConversionService.Converter;

public abstract class TypeConverter<T> implements Converter<T> {
	
// ------------------------------------------------------------------------
	
	protected abstract T convertType(String stringValue);	
		
	/**
	 * Constructor
	 * @param clazz
	 */
	public TypeConverter() {
	}
	
// ------------------------------------------------------------------------

	@Override
	public final T convert(String name, Map<String, String[]> data) {
		String[] values = data.get(name);
		if ((values != null) && (values.length > 0)) {
			return convertType(values[0]);
		} else {
			return convertType("");
		}
	}
	
// ------------------------------------------------------------------------

}
