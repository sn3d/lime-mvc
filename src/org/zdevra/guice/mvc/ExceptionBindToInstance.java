package org.zdevra.guice.mvc;

import com.google.inject.Injector;


/**
 * This binding class bind an exception to concrete instance 
 * of the {@link ExceptionHandler}. 
 * 
 * @see ExceptionHandler
 * @see ExceptionBind
 * @see GuiceExceptionResolver
 */
public class ExceptionBindToInstance extends ExceptionBind {

	private final ExceptionHandler handler;

	public ExceptionBindToInstance(ExceptionHandler handler, Class<? extends Throwable> exceptionClass, int order) {
		super(exceptionClass, order);
		this.handler = handler;
	}

	@Override
	public ExceptionHandler getHandler(Injector injetor) {
		return handler;
	}

}
