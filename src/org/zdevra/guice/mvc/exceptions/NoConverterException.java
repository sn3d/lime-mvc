package org.zdevra.guice.mvc.exceptions;

/**
 * Lime MVC is not able convert the String value from HTTP request to the
 * controller's method's argument type. Check the method's arguments or 
 * define new converter for the missing type.
 *
 */
public class NoConverterException extends MvcException {
	public NoConverterException(Class<?> type) {
		super("No converter for String -> " + type.getCanonicalName() + " is defined.");
	}
}
