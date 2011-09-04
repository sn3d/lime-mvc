package org.zdevra.guice.mvc.converters;

import java.util.Map;

import org.zdevra.guice.mvc.ConversionService.Converter;

/**
 * It's separated converter for int[] arrays.

 */
public final class IntArrayConverter implements Converter<int[]> {
		
// ------------------------------------------------------------------------
		
	/**
	 * Hidden constructor. The converters are instantiated via his Factories.
	 */
	private IntArrayConverter() {
	}
	
// ------------------------------------------------------------------------
		
	@Override
	public int[] convert(String name, Map<String, String[]> data) {
		String[] values = data.get(name);		
		if ((values == null) || (values.length == 0)) {
			return new int[] {};
		}
		
		int[] out = new int[values.length];
		for(int i = 0; i < values.length; ++i) {
			out[i] = Integer.parseInt(values[i]);
		}
		
		return out;
	}
	
	
// ------------------------------------------------------------------------

}
