package org.zdevra.guice.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface you will implement when you want to do some global 
 * pre/post processing of the requests and responses.
 *  
 */
public interface InterceptorHandler {

	/**
	 * Dispatcher calls the preHandler before controller's method invokation.
	 * 
	 * @param request
	 * @param response
	 * 
	 * @return true if the execution should continue in chain execution, false if you want to 
	 * stop excution of the interceptor chain. The controller shouldn't be invoked when the 
	 * interceptor returns false and the request's processing ends.
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response);
	
	
	/**
	 * Dispatcher calls the postHandler after controller's method execution but before
	 * the view is rendering.
	 * 
	 * @param request
	 * @param response
	 * @param mav
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, ModelAndView mav);
	
	
	/**
	 * Method is invoked at the end of request processing when the view has been rendered and only in
	 * case when all interceptors return true.
	 * 
	 * You will implement here resource releasing.  
	 * 
	 * @param request
	 * @param response
	 * @param e
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Throwable e);


	
}
