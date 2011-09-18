package org.zdevra.guice.mvc.exceptions;

import org.zdevra.guice.mvc.ConversionService;

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

    public NoConverterException(Class<? extends ConversionService.ConverterFactory> factory, Class<?> type) {
        super("Invalid converter factory '" + factory.getName() + "' for String -> " + type.getCanonicalName() + " is defined.");
    }
}
