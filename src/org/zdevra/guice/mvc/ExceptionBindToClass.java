package org.zdevra.guice.mvc;

import com.google.inject.Injector;

/**
 * This binding class bind an exception to handler's class. 
 * A handler lifecycle is under Guice's control.
 * 
 * @see ExceptionHandler
 * @see ExceptionBind
 * @see GuiceExceptionResolver
 */
class ExceptionBindToClass extends ExceptionBind {
	
	private final Class<? extends ExceptionHandler> handlerClass;

	public ExceptionBindToClass(Class<? extends ExceptionHandler> handlerClass, Class<? extends Throwable> exceptionClass, int order) {
		super(exceptionClass, order);
		this.handlerClass = handlerClass; 
	}

	@Override
	public ExceptionHandler getHandler(Injector injector) {
		return injector.getInstance(handlerClass);
	}

}
