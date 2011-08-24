package org.zdevra.guice.mvc.exceptions;

import javax.servlet.http.HttpServletRequest;

/**
 * All view exceptions are derived from this exception
 */
public class ViewException extends MvcException {
	public ViewException(String viewId, HttpServletRequest request, Throwable e) {
		super("Error occured in view '" + viewId + " when URL is '" + request.getRequestURI(), e);
	}
}
