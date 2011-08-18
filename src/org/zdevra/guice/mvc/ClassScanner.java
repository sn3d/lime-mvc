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
	public ClassInvoker scan(Class<?> controllerClass, Injector injector) throws Exception {
		Controller controllerAnotation = controllerClass.getAnnotation(Controller.class);
		if (controllerAnotation == null) {
			throw new IllegalStateException("Class is not defined as a controller. Missing @Controller annotation.");
		}

		//scan session attributes & default view
		List<String> sessionAttrList = Arrays.asList(controllerAnotation.sessionAttributes());				
				
		//scan methods
		List<Method> methods = Arrays.asList(controllerClass.getMethods());
		List<MethodInvoker> scannedInvokers = new LinkedList<MethodInvoker>();
		for (Method method : methods) {
			
			RequestMapping reqMapping = method.getAnnotation(RequestMapping.class);
			if (reqMapping != null) {
				MethodInvoker invoker = MethodInvokerImpl.createInvoker(controllerClass, method, reqMapping, injector);
				MethodInvoker filteredInvoker = new MethodInvokerFilter(reqMapping, invoker);
				scannedInvokers.add(filteredInvoker);							
			}			
		}
		
		return new ClassInvoker(controllerClass, scannedInvokers, sessionAttrList); 
	}
	
}
