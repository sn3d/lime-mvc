/*****************************************************************************
 * Copyright 2011 Zdenko Vrabel
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

import java.lang.reflect.Method;

import org.zdevra.guice.mvc.exceptions.MethodInvokingException;

/**
 * This class is responsible for invoking controller's methods.
 * <p>
 * 
 * If the {@link MvcDispatcherServlet} make decission that is called
 * GET or another HTTP request for this controller, then is each method
 * of the controller called via this class.
 * <p>
 * 
 * This class is also responsible for scanning and holding data about controller's 
 * methods, which are and how are mapped.
 * <p>
 *
 */
public class ControllerMethodInvoker implements MethodInvoker {
	
// ------------------------------------------------------------------------
		
	private final View methodView;
	private final String resultModelName;
	private final Method method;
	private final MethodParamsBuilder paramsBuilder;
	
// ------------------------------------------------------------------------
	
	/**
	 * Factory method, wich build invoker for concrete given controller's method. 
	 */
	public static MethodInvoker createInvoker(Method method, MethodParamsBuilder paramBuilder) 
	{
		RequestMapping reqMapping = method.getAnnotation(RequestMapping.class);
		if (reqMapping == null) {
			return null;
		}
				
		String resultNameInModel = reqMapping.nameOfResult();
		View methodView = JspView.create(reqMapping.toView());
		
		ControllerMethodInvoker invoker 
			= new ControllerMethodInvoker(
					method,
					resultNameInModel,
					methodView,
					paramBuilder);
		
		MethodInvoker res = new MethodInvokerFilter(reqMapping, invoker);
		return res;						
	}
	
	
	/**
	 * Hidden constructor
	 * 
	 * @param method
	 * @param path
	 * @param reqType
	 */
	protected ControllerMethodInvoker(Method method,  String resultModelName, View methodView, MethodParamsBuilder paramBuilder) 
	{
		this.methodView = methodView;
		this.resultModelName = resultModelName;
		this.method = method;
		this.paramsBuilder = paramBuilder;
	}
	
// ------------------------------------------------------------------------
	
	@Override
	public String toString() {
		return "ControllerMethodInvoker [method=" + method.getName() + " ]";
	}

		
	/* (non-Javadoc)
	 * @see org.zdevra.guice.mvc.MethodInvoker#invoke(java.lang.Object, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.zdevra.guice.mvc.Model, org.zdevra.guice.mvc.RequestType)
	 */
	@Override
	public ModelAndView invoke(InvokeData data) 
	{ 
		try {
			Object controllerObj = data.getController();						
			
			Object[] args = paramsBuilder.getValues(data);			
			Object result = method.invoke(controllerObj, args);
			ModelAndView mav = processResult(result);
			
			args = null;			
			return mav;
		} catch (Exception e) {
			throw new MethodInvokingException(method, e);
		}
	}

	
	private ModelAndView processResult(Object result) {
		ModelAndView out = new ModelAndView(methodView);

		if (result == null) {
			return out;
		} else if (result instanceof Model) {
			Model resultModel = (Model) result;
			out.addModel(resultModel);			
		} else if (result instanceof ModelAndView) {
			return (ModelAndView) result;
		} else if (result instanceof View) {
			View resultView = (View)result;
			out.addView(resultView);
		} else {			
			String name = getResultModelName();
			out.getModel().addObject(name, result);
		}
		
		return out;
	}
	
	
	private String getResultModelName() {
		if (this.resultModelName == null || this.resultModelName.length() == 0) {
			return this.method.getName();
		}
		return this.resultModelName;
	}
	
// ------------------------------------------------------------------------
	
	
}
