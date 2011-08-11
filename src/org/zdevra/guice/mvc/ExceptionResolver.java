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
 * 
 * 
 * Example:
 * <pre class="prettyprint">
 * public void configureControllers() {
 *    ...
 *    bindException(Custom1Exception.class).toHandler(Custom1ExceptionHandler.class);
 *    bindException(Custom2Exception.class).toHandlerInstance( new Custom2ExceptionHandler() );
 *    ...
 * }
 * </pre>
 *  
 * @see ExceptionHandler
 * @see GuiceExceptionResolver
 * @see MvcModule
 */
public interface ExceptionResolver {
	
	public static final String DEFAULT_EXCEPTIONHANDLER_NAME = "defaultExceptionHandler";

	/**
	 * Method is invoked in {@link MvcDispatcherServlet}, when a controller
	 * throw some exception. This method go through all registered
	 * exception handlers and invoke correct handler for that exception.
	 */
	public abstract void handleException(Throwable t, HttpServlet servlet, HttpServletRequest req, HttpServletResponse resp);

}