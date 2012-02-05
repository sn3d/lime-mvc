package org.zdevra.guice.mvc;

import org.zdevra.guice.mvc.ConversionService.ConverterFactory;

import com.google.inject.Binder;

@Deprecated
class ConversionServiceBuilder extends MultibinderBuilder<ConverterFactory> {
// ------------------------------------------------------------------------
	
	/**
	 * Constructor
	 */
	public ConversionServiceBuilder(Binder binder) {
		super(binder, ConverterFactory.class);
	}
		
// ------------------------------------------------------------------------

}
