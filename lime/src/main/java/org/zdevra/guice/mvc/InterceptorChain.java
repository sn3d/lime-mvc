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

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * CLass represents the Chain of interceptor handlers and provide basic
 * methods for preprocessing and postprocessing for the whole chain.
 *
 */
class InterceptorChain {

    private final Collection<InterceptorHandler> handlers;

    /**
     * Constructor
     * @param handlers
     */
    InterceptorChain(Collection<InterceptorHandler> handlers) {
        this.handlers = Collections.unmodifiableCollection(handlers);
    }

    /**
     * Constructor for em
     * @param handlers
     */
    InterceptorChain() {
        this.handlers = Collections.emptyList();
    }

    public boolean isEmpty() {
        if (handlers.size() > 0) {
            return false;
        }
        return true;
    }

    /**
     * is called before the controller's method is invoked
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response) {
        for (InterceptorHandler handler : handlers) {
            boolean res = handler.preHandle(request, response);
            if (!res) {
                return false;
            }
        }
        return true;
    }

    /**
     * is called after the controller's method is invoked
     * @param request
     * @param response
     * @param mav
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
        for (InterceptorHandler handler : handlers) {
            handler.postHandle(request, response, mav);
        }
    }


    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Throwable e) {
        for (InterceptorHandler handler : handlers) {
            handler.afterCompletion(request, response, e);
        }
    }


    /**
     * Method creates new extended chain and put new collection of handlers into new chain.
     * 
     * @param handlers
     * @return
     */
    public InterceptorChain putInterceptorHandlers(Collection<InterceptorHandler> newHandlers) {
        List<InterceptorHandler> newChainHandlers = new LinkedList<InterceptorHandler>();
        newChainHandlers.addAll(this.handlers);
        newChainHandlers.addAll(newHandlers);
        return new InterceptorChain(newChainHandlers);
    }
// ------------------------------------------------------------------------
}
