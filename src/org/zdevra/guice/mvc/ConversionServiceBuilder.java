package org.zdevra.guice.mvc;

import org.zdevra.guice.mvc.ConversionService.ConverterFactory;

import com.google.inject.Binder;
import com.google.inject.multibindings.Multibinder;

public class ConversionServiceBuilder {
// ------------------------------------------------------------------------
	
	private final Multibinder<ConverterFactory> convertorsBinder;
		
// ------------------------------------------------------------------------
	
	/**
	 * Constructor
	 */
	public ConversionServiceBuilder(Binder binder) {
		this.convertorsBinder = Multibinder.newSetBinder(binder, ConverterFactory.class);
	}
	
	
	public void registerConverter(Class<? extends ConverterFactory> factoryClazz) {
		convertorsBinder.addBinding().to(factoryClazz).asEagerSingleton();
	}
	
	
	public void registerConverter(ConverterFactory factory) {
		convertorsBinder.addBinding().toInstance(factory);
	}
	
// ------------------------------------------------------------------------

}
