package org.zdevra.guice.mvc;

import org.zdevra.guice.mvc.ConversionService.ConvertorFactory;

import com.google.inject.Binder;
import com.google.inject.multibindings.Multibinder;

public class ConversionServiceBuilder {
// ------------------------------------------------------------------------
	
	private final Multibinder<ConvertorFactory> convertorsBinder;
		
// ------------------------------------------------------------------------
	
	/**
	 * Constructor
	 */
	public ConversionServiceBuilder(Binder binder) {
		this.convertorsBinder = Multibinder.newSetBinder(binder, ConvertorFactory.class);
	}
	
	
	public void registerConvertor(Class<? extends ConvertorFactory> factoryClazz) {
		convertorsBinder.addBinding().to(factoryClazz).asEagerSingleton();
	}
	
	
	public void registerConvertor(ConvertorFactory factory) {
		convertorsBinder.addBinding().toInstance(factory);
	}
	
// ------------------------------------------------------------------------

}
