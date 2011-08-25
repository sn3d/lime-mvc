package org.zdevra.guice.mvc.exceptions;

import javax.servlet.http.HttpServletRequest;

/**
 * Exception is throwed when controller has selected no view.
 */
public class NoViewException extends MvcException {
	public NoViewException(HttpServletRequest req) {
		super("Controller has selected invalid or undefined view (" + req.getRequestURI() + ")");
	}
}
