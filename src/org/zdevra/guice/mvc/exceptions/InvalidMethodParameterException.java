package org.zdevra.guice.mvc.exceptions;

import org.zdevra.guice.mvc.parameters.ParamMetadata;

/**
 * The exception is throwed when the controller's method has the parameter
 * which source of data is undefined (parameter is without any known annotation 
 * like {@link UriParameter} etc..
 * 
 * Solution: Specify the source of data by annotating controller method's 
 * argument/parameter with some param. annotation
 */
public class InvalidMethodParameterException extends MvcException {
	
	/**
	 * Constructor
	 * @param metadata
	 */
	public InvalidMethodParameterException(ParamMetadata metadata) {
		super("Invalid source of data for parameter. Check the parameters in the controller's method " + metadata.getMethod().getName() + "().");
	}
}
