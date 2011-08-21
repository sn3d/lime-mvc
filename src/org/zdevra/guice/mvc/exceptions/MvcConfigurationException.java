package org.zdevra.guice.mvc.exceptions;

public class MvcConfigurationException extends MvcException {

	public MvcConfigurationException(Throwable e) {
		super("ERROR in Mvc initialization & configuration. Look on below exception what causes this error.", e);
	}


}
