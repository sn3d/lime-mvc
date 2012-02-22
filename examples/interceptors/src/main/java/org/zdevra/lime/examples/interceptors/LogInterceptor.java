package org.zdevra.lime.examples.interceptors;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zdevra.guice.mvc.InterceptorHandler;
import org.zdevra.guice.mvc.ModelAndView;

public class LogInterceptor implements InterceptorHandler {
	
	private static final Logger logger = Logger.getLogger(LogInterceptor.class.getName());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response) 
	{
		logger.log(Level.INFO, "processing " + request.getRequestURL().toString());
		return true;
	}

	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) 
	{
		logger.log(Level.INFO, "model:" + mav.getModel()); 
	}

	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Throwable e) 
	{
		logger.log(Level.INFO, "request complete");
	}

}
