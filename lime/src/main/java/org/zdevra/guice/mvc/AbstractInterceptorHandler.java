/*****************************************************************************
 * Copyright 2012 Zdenko Vrabel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 *****************************************************************************/
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
