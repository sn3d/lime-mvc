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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.google.inject.Injector;

/**
 * The method provide scanning of Controller class
 * and produce the invoker for controller. It's factory
 * class for {@link ClassInvoker}
 *
 * @see ClassInvoker
 */
public class ClassScanner {

	/**
	 * Method do scanning of controller's class and produce
	 * {@link ClassInvoker} object
	 * 
	 * @param controllerClass
	 * @param injector
	 * 
	 * @return 
	 * @throws Exception
	 */
	public ClassInvoker scan(Class<?> controllerClass, Injector injector) 
		throws Exception 
	{
		Controller controllerAnotation = controllerClass.getAnnotation(Controller.class);
		if (controllerAnotation == null) 
		{
			throw new IllegalStateException("Class is not defined as a controller. Missing @Controller annotation.");
		}

		//scan session attributes & default view
		List<String> sessionAttrList = Arrays.asList(controllerAnotation.sessionAttributes());				
				
		//scan methods
		List<Method> methods = Arrays.asList(controllerClass.getMethods());
		List<MethodInvoker> scannedInvokers = new LinkedList<MethodInvoker>();
		for (Method method : methods) 
		{			
			MappingData reqMappingData = mappingDataForNewAnot(method);
			if (reqMappingData == null) {
				reqMappingData = mappingDataForOldAnot(method);
			}
			
			if (reqMappingData != null) 
			{
				reqMappingData.injector = injector;
				reqMappingData.controllerClass = controllerClass;
				reqMappingData.method = method;
				MethodInvoker invoker = MethodInvokerImpl.createInvoker(reqMappingData);
				MethodInvoker filteredInvoker = new MethodInvokerFilter(reqMappingData, invoker);
				scannedInvokers.add(filteredInvoker);							
			}
		}
		
		return new ClassInvoker(controllerClass, scannedInvokers, sessionAttrList); 
	}
	
	
	/**
	 * old version of parsing, it should be removed in next versions
	 */
	@Deprecated
	private MappingData mappingDataForOldAnot(Method method) 
	{
		RequestMapping anotOld = method.getAnnotation(RequestMapping.class);
		if (anotOld == null) 
		{
			return null;
		}
		
		MappingData reqMapping = new MappingData(
				null,
				null,
				anotOld.requestType(),
				anotOld.path(),
				anotOld.nameOfResult(),
				null );
		
		return reqMapping;
	}
	
	
	/**
	 * new version of mapping annotations
	 */
	private MappingData mappingDataForNewAnot(Method method) 
	{
		Path path =  method.getAnnotation(Path.class);
		if (path == null) {
			return null;
		}
		
		MappingData reqMapping = new MappingData(
				null,
				null,
				HttpMethodType.ALL,
				path.value(),
				method.getName(),
				null );		
		
		ModelName resultName = method.getAnnotation(ModelName.class);
		if (resultName != null) {
			reqMapping.resultName = resultName.value();
		}
		
		//resolve HTTP method
		if (method.getAnnotation(GET.class) != null) {
			reqMapping.httpMethodType = HttpMethodType.GET;
		} else if (method.getAnnotation(POST.class) != null) {
			reqMapping.httpMethodType = HttpMethodType.POST;
		} else if (method.getAnnotation(PUT.class) != null) {
			reqMapping.httpMethodType = HttpMethodType.PUT;
		} else if (method.getAnnotation(DELETE.class) != null) {
			reqMapping.httpMethodType = HttpMethodType.DELETE;
		} else if (method.getAnnotation(HEAD.class) != null) {
			reqMapping.httpMethodType = HttpMethodType.HEAD;
		} else {
			reqMapping.httpMethodType = HttpMethodType.ALL;
		}
		
		return reqMapping;
	}
}
