package org.zdevra.guice.mvc;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;

/**
 * The handler is rendering a view page for the exception.
 */
class ViewExceptionHandler implements ExceptionHandler {
	
// ------------------------------------------------------------------------
	
	private final View errorView;
	@Inject private ViewResolver viewResolver;
	
// ------------------------------------------------------------------------
		
	public ViewExceptionHandler(View errorView) {
		this.errorView = errorView;
	}
	
// ------------------------------------------------------------------------

	@Override
	public void handleException(Throwable t, HttpServlet servlet, HttpServletRequest req, HttpServletResponse resp) 
	{
		viewResolver.resolve(errorView, servlet, req, resp);
	}

// ------------------------------------------------------------------------
}
