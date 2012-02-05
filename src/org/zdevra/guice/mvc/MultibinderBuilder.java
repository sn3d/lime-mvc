package org.zdevra.guice.mvc;

import com.google.inject.Binder;
import com.google.inject.multibindings.Multibinder;

class MultibinderBuilder<T> {
	
// ------------------------------------------------------------------------

	protected final Binder binder;
	protected final Multibinder<T> multibinder;
		
// ------------------------------------------------------------------------
		
	/**
	 * Constructor
	 */
	public MultibinderBuilder(Binder binder, Class<T> clazz) 
	{
		this.binder = binder;
		this.multibinder = Multibinder.newSetBinder(binder, clazz);
	}
				
	public void registerClass(Class<? extends T> clazz) {
		multibinder.addBinding().to(clazz).asEagerSingleton();
	}
		
	public void registerInstance(T instance) {		
		binder.requestInjection(instance);
		multibinder.addBinding().toInstance(instance);
	}
		
// ------------------------------------------------------------------------

}
