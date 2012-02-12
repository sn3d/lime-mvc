package org.zdevra.guice.mvc;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * CLass represents the Chain of interceptor handlers and provide basic
 * methods for preprocessing and postprocessing for the whole chain.
 *
 */
class InterceptorChain {
	
// ------------------------------------------------------------------------
	
	private final Collection<InterceptorHandler> handlers;

// ------------------------------------------------------------------------
	
	/**
	 * Constructor
	 * @param handlers
	 */
	InterceptorChain(Collection<InterceptorHandler> handlers)
	{
		this.handlers = Collections.unmodifiableCollection(handlers);
	}	
	
	/**
	 * Constructor for em
	 * @param handlers
	 */
	InterceptorChain() {
		this.handlers = Collections.emptyList();
	}

// ------------------------------------------------------------------------
	
	
	public boolean isEmpty()
	{
		if (handlers.size() > 0) {
			return false;
		}
		return true;
	}
	
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response)
	{
		for (InterceptorHandler handler: handlers) {
			boolean res = handler.preHandle(request, response);
			if (!res) {
				return false;
			}
		}
		return true;
	}
	
	
	public void postHandle(HttpServletRequest request, HttpServletResponse response, ModelAndView mav)
	{
		for (InterceptorHandler handler: handlers) {
			handler.postHandle(request, response, mav);
		}		
	}
	
	
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Throwable e)
	{
		for (InterceptorHandler handler: handlers) {
			handler.afterCompletion(request, response, e);
		}		
	}
	
	
	/**
	 * Method creates new extended chain and put new collection of handlers into new chain.
	 * 
	 * @param handlers
	 * @return
	 */
	public InterceptorChain putInterceptorHandlers(Collection<InterceptorHandler> newHandlers) 
	{
		List<InterceptorHandler> newChainHandlers = new LinkedList<InterceptorHandler>();
		newChainHandlers.addAll(this.handlers);
		newChainHandlers.addAll(newHandlers);
		return new InterceptorChain(newChainHandlers);
	}
		

// ------------------------------------------------------------------------

}
