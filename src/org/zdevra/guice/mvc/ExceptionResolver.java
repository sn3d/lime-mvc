package org.zdevra.guice.mvc;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ExceptionResolver {

	/**
	 * Method is invoked by MvcDispatcherServlet when controller
	 * throw some exception. This method go through all registered
	 * exception handlers and invoke correct handlers for that exception.
	 */
	public abstract void handleException(Throwable t, HttpServlet servlet,
			HttpServletRequest req, HttpServletResponse resp);

}