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

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.zdevra.guice.mvc.exceptions.MethodInvokingException;
import org.zdevra.guice.mvc.parameters.ParamMetadata;
import org.zdevra.guice.mvc.parameters.ParamProcessor;
import org.zdevra.guice.mvc.parameters.ParamProcessorsService;


public class MethodInvokerImpl implements MethodInvoker {
	
/*---------------------------- m. variables ----------------------------*/
	
	private final View defaultView;
	private final String resultName;
	private final Method method;
	private final List<ParamProcessor> paramProcs;
	
/*---------------------------- constructors ----------------------------*/
	
	/**
	 * The factory method construct a method invoked which has no view and
	 * result of the invoked method is stored into test-result
	 */
	public static MethodInvoker createInvokerForTest(Method method, ParamProcessorsService paramService) {
		List<ParamProcessor> processors = scanParams(method, paramService, null);
		return new MethodInvokerImpl(null, "test-result", method, processors);
	}
	
	/**
	 * The regular factory method used by Lime
	 * 
	 * @param method
	 * @param reqMapping
	 * @param paramService
	 * @param convertService
	 * @return
	 */
	public static MethodInvoker createInvoker(Method method, RequestMapping reqMapping, ParamProcessorsService paramService, ConversionService convertService) {		
		String resultName = reqMapping.nameOfResult();
		View methodView = JspView.create(reqMapping.toView());
		
		List<ParamProcessor> processors = scanParams(method, paramService, convertService);				
		MethodInvoker invoker = new MethodInvokerImpl(methodView, resultName, method, processors);				
		return invoker; 
	}

	
	/**
	 * Hidden constructor. The Invoker is construted throught the factory methods.
	 * @param defaultView
	 * @param resultName
	 * @param method
	 * @param paramProcs
	 */
	private MethodInvokerImpl(View defaultView, String resultName, Method method, List<ParamProcessor> paramProcs) {
		this.defaultView = defaultView;
		this.resultName = resultName;
		this.method = method;
		this.paramProcs = Collections.unmodifiableList(paramProcs);
	}
		
/*----------------------------------------------------------------------*/
	
	
	private static final List<ParamProcessor> scanParams(Method method, ParamProcessorsService paramService, ConversionService convertService) {			
		Annotation[][] annotations = method.getParameterAnnotations();		
		Class<?>[] types = method.getParameterTypes();
		List<ParamProcessor> result = new LinkedList<ParamProcessor>();
		
		for (int i = 0; i < types.length; ++i) {
			ParamMetadata metadata = new ParamMetadata(types[i], annotations[i], convertService, method);
			ParamProcessor processor = paramService.createProcessor(metadata);
			result.add(processor);
		}

		return result;
	}
	
	
	public ModelAndView invoke(InvokeData data) 
	{
		try {
			Object controllerObj = data.getController();
	
			Object[] args = getValues(data);			
			Object result = method.invoke(controllerObj, args);
			ModelAndView mav = processResult(result);
			
			args = null;			
			return mav;			
		} catch (IllegalArgumentException e) {
			throw new MethodInvokingException(method, e);
		} catch (IllegalAccessException e) {
			throw new MethodInvokingException(method, e);
		} catch (InvocationTargetException e) {
			throw new MethodInvokingException(method, e.getCause());
		}
	}
	
	
	private Object[] getValues(InvokeData data) 
	{		
		Object[] out = new Object[paramProcs.size()];
		for (int i = 0; i < paramProcs.size(); ++i) {
			ParamProcessor processor = paramProcs.get(i);
			out[i] = processor.getValue(data);			
		}		
		return out;
	}
	
	
	private ModelAndView processResult(Object result) 
	{
		ModelAndView out = new ModelAndView(this.defaultView);

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
		if (this.resultName == null || this.resultName.length() == 0) {
			return this.method.getName();
		}
		return this.resultName;
	}
	
/*----------------------------------------------------------------------*/

}
