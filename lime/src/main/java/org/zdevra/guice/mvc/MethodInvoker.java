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
 * Interface to intercept invocation of controller's method.
 * The {@link MethodInvokerImpl} implementing an execution
 * of the controller's method. This can be decorated
 * by another functionality like {@link MethodInvokerFilter}.
 * 
 * @see MethodInvokerFilter
 * @see MethodInvokerImpl
 */
interface MethodInvoker extends Comparable<MethodInvoker> {

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

    /**
     * This method returns the priority level. 0 is default priority level. Decreasing
     * number means the priority is higher and increasing number means lower priority.
     * That means the level Integer.MIN_VALUE is the highest priority and Integer.MAX_VALUE
     * on other side means the lowest possible priority.
     * @return
     */
    public abstract int getPriority();
}