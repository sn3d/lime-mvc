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


/**
 * Interface to intercept invokation of controller's method.
 * The {@link MethodInvokerImpl} implementing an execution
 * of the controller's method. This can be decorated
 * by another functionality like {@link MethodInvokerFilter}.
 * 
 * @see MethodInvokerFilter
 * @see MethodInvokerImpl
 */
interface MethodInvoker {

	/**
	 * This method is called by MvcServletDispatcher and invoke
	 * concrete method of controller with correct parameters.
	 * 
	 * Method is called always when is requested controller.
	 * 
	 * @return model and view as a product of controller's execution where model is rendered by view.
	 * When implementation returns null then the view resolver and rendering is skipped. 
	 */
	public abstract ModelAndView invoke(InvokeData data);

}