package org.zdevra.guice.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;

/**
 * Servlet provide direct view rendering without any controlling.
 *
 */
class ViewServlet extends HttpServlet {
	
// ------------------------------------------------------------------------
	
	private final View view;
	private ViewResolver viewResolver;
	private ExceptionResolver exceptionResolver;
	
// ------------------------------------------------------------------------
	
	public ViewServlet(View view) {
		this.view = view;
	}
	
// ------------------------------------------------------------------------
	
	@Inject
	public final void setViewResolver(ViewResolver viewResolver) {
		this.viewResolver = viewResolver;
	}
	
	@Inject
	public final void setExceptionResolver(ExceptionResolver exceptionResolver) {
		this.exceptionResolver = exceptionResolver;
	}
		
	private void redirectToView(HttpServletRequest req, HttpServletResponse resp) {
		try {
			viewResolver.resolve(view, this, req, resp);
		} catch (Throwable e) {
			exceptionResolver.handleException(e, this, req, resp);
		}
	}
	
// ------------------------------------------------------------------------

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		this.redirectToView(req, resp);	
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		this.redirectToView(req, resp);
	}

	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		this.redirectToView(req, resp);
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		this.redirectToView(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		this.redirectToView(req, resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		this.redirectToView(req, resp);
	}

	@Override
	protected void doTrace(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		this.redirectToView(req, resp);
	}
	
// ------------------------------------------------------------------------
	
}
