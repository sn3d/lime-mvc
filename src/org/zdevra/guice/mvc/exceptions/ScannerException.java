package org.zdevra.guice.mvc.exceptions;

/**
 * The exception is throwed by during MVC initialization
 * when the controllers are scanned and invokers are created. 
 */
public class ScannerException extends MvcException {

	public ScannerException(Class<?> controllerClass, Throwable e) {
		super("Exception in scanning of the controller:" + controllerClass.getName(), e);
	}

}
