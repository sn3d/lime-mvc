package org.zdevra.guice.mvc;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This interface is used for resolving exceptions which are throwed 
 * from controllers. 
 * 
 * The default implementation of resolving in Lime MVC is used {@link GuiceExceptionResolver}.
 * It can be changed easy in @{link MvcModule}'s configureControllers() method.
 * 
 * Example:
 * <pre>
 * public void configureControllers() {
 * 
 * }
 * </pre>
 * 
 * @see
 */
public interface ExceptionResolver {

	/**
	 * Method is invoked in {@link MvcDispatcherServlet}, when a controller
	 * throw some exception. This method go through all registered
	 * exception handlers and invoke correct handler for that exception.
	 */
	public abstract void handleException(Throwable t, HttpServlet servlet, HttpServletRequest req, HttpServletResponse resp);

}