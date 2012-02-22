/**
 * 
 */
package org.zdevra.guice.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Abstract handler for simplest implementation when you want implement only
 * pre or post handing.
 */
public abstract class AbstractInterceptorHandler implements InterceptorHandler {

	/** 
	 * @see org.zdevra.guice.mvc.InterceptorHandler#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response) 
	{
		return true;
	}

	/**
	 * @see org.zdevra.guice.mvc.InterceptorHandler#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.zdevra.guice.mvc.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) 
	{
		//do nothing
	}

	/**
	 * @see org.zdevra.guice.mvc.InterceptorHandler#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Throwable)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Throwable e) 
	{
		//do nothing
	}

}
