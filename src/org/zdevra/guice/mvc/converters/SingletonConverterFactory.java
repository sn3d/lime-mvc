package org.zdevra.guice.mvc.converters;

import java.lang.annotation.Annotation;

import org.zdevra.guice.mvc.ConversionService.Converter;
import org.zdevra.guice.mvc.ConversionService.ConverterFactory;

/**
 * Provides the singleton functionality for converters and his factories.
 * The converter is singletoned and is not instanciated again and again.
 */
class SingletonConverterFactory implements ConverterFactory {
	
// ------------------------------------------------------------------------
	
	private final Converter converterInstance;
	private final Class<?> forClass;
	
// ------------------------------------------------------------------------
	
	/**
	 * Constructor
	 * @param forClass
	 * @param converter
	 */
	public SingletonConverterFactory(Class<?> forClass, Converter converter) {
		this.forClass = forClass;
		this.converterInstance = converter;		
	}
	

	/**
	 * Method check whether the type is matching to converter and returns the
	 * instance of the appropriate converter 
	 */
	@Override
	public Converter createConvertor(Class<?> type, Annotation[] annotations) {
		if (type == forClass) {
			return converterInstance;
		}
		return null;
	}
	
// ------------------------------------------------------------------------
}
