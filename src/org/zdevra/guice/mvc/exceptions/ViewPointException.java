package org.zdevra.guice.mvc.exceptions;

import javax.servlet.http.HttpServletRequest;

/**
 * Exception it thrown when ViewPoint's rendering fails for some reason.
 */
public class ViewPointException extends MvcException {

	public ViewPointException(String msg, HttpServletRequest request, Throwable e) {
		super(msg, e);
	}
}
