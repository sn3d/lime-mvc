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

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

/**
 * The class representing data structure which provide
 * and separate invocation of the class's methods. 
 */
public class ClassInvoker {
	
// ------------------------------------------------------------------------
	
	private final Class<?> controllerClass;
	private final Collection<String> sessionAttrs;
	private final Collection<MethodInvoker> methodInvokers;
	
// ------------------------------------------------------------------------
	
	/**
	 * Constructor
	 * 
	 * @param controllerClass
	 * @param methodInvokers
	 * @param sessionAttrList 
	 */
	public ClassInvoker(Class<?> controllerClass, Collection<MethodInvoker> methodInvokers, List<String> sessionAttrList) {
		this.controllerClass = controllerClass;
		this.methodInvokers = Collections.unmodifiableCollection(methodInvokers);
		this.sessionAttrs = Collections.unmodifiableCollection(sessionAttrList);
	}
	
// ------------------------------------------------------------------------
	
	/**
	 * It invokes concrete methods of the controller class.
	 * @param data
	 */
	public ModelAndView invoke(InvokeData data) {		
		ModelAndView mav = new ModelAndView();		
		int invokedcount = 0;				
		for (MethodInvoker invoker : this.methodInvokers) {
			ModelAndView methodMav = invoker.invoke(data);			
			if (methodMav != null) {
				mav.mergeModelAndView(methodMav);
				invokedcount++;
			}
		}
		
		if (invokedcount == 0) {
			return null;
		}
		
				
		return mav;	
	}
	
	
	/**
	 * Method moves the data defined in {@literal @}Controller's
	 * sessionAttribute from model to session. 
	 * 
	 * @param m
	 * @param session
	 */
	public void moveDataToSession(Model m, HttpSession session) {
		m.moveObjectsToSession(sessionAttrs, session);
	}

// ------------------------------------------------------------------------
	
	public Class<?> getControllerClass() {
		return controllerClass;
	}
	
// ------------------------------------------------------------------------
}
