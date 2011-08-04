package org.zdevra.guice.mvc;

import com.google.inject.Injector;

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
