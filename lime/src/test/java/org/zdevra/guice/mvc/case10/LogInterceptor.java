package org.zdevra.guice.mvc.case10;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zdevra.guice.mvc.InterceptorHandler;
import org.zdevra.guice.mvc.ModelAndView;

import com.google.inject.Singleton;

@Singleton
public class LogInterceptor implements InterceptorHandler {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response) 
	{
		Case10Log.getInstance().log("preHandle executed");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) 
	{
		Case10Log.getInstance().log("postHandle executed");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Throwable e) 
	{
		Case10Log.getInstance().log("afterCompletion executed");		
	}

}
