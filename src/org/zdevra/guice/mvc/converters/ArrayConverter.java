package org.zdevra.guice.mvc.converters;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.zdevra.guice.mvc.ConversionService.Converter;

public abstract class ArrayConverter<T> implements Converter<T[]> {
	
// ------------------------------------------------------------------------
	
	private final T[] empty;
	
// ------------------------------------------------------------------------

	protected abstract T convertItem(String value);
	
// ------------------------------------------------------------------------
	
	public ArrayConverter(T[] emptyArray) {
		this.empty = emptyArray;
	}

	@Override
	public final T[] convert(String name, Map<String, String[]> data) {		
        String[] values = data.get(name);
        if (values == null) {
            return empty;
        }

        List<T> out = new LinkedList<T>();
        for (int i = 0; i < values.length; ++i) {
            T itm = convertItem(values[i]);
            if (itm != null) {
                out.add(itm);
            }
        }
		return out.toArray(empty);
	}
	
// ------------------------------------------------------------------------

}
