package org.zdevra.guice.mvc;

import org.zdevra.guice.mvc.parameters.ParamProcessorFactory;
import org.zdevra.guice.mvc.parameters.ParamProcessorsService;

import com.google.inject.Binder;
import com.google.inject.multibindings.Multibinder;

/** 
 * The builder is responsible for building and
 * registering of parameter processors into
 * parameter processor service.
 * 
 * @see MvcModule
 * @see ParamProcessorFactory
 * @see ParamProcessorsService
 */

public class ParamProcessorBuilder {
	
// ------------------------------------------------------------------------
	
	private final Multibinder<ParamProcessorFactory> paramBinder;
	
// ------------------------------------------------------------------------
	
	public ParamProcessorBuilder(Binder binder) {
		this.paramBinder = 
				Multibinder.newSetBinder(binder, ParamProcessorFactory.class);
	}

	public void registerParamProc(Class<? extends ParamProcessorFactory> paramProcFactory) {
		paramBinder.addBinding().to(paramProcFactory);
	}
	
// ------------------------------------------------------------------------
}
