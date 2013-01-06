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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is decorator class. The decorator invoke only these methods of 
 * the controller, which are passed throught URL check and the HTTP request 
 * has required type (RequestType)
 *
 */
class MethodInvokerFilter extends MethodInvokerDecorator {
    /*---------------------------- m. variables ----------------------------*/

    private final Pattern pathPattern;
    private final HttpMethodType requestTypeFilter;

    /*---------------------------- constructors ----------------------------*/
    /**
     * Constructor
     */
    public MethodInvokerFilter(MappingData mapping, MethodInvoker decoratedInvoker) {
        super(decoratedInvoker);
        this.pathPattern = Pattern.compile("(?i)" + mapping.path + "$");
        this.requestTypeFilter = mapping.httpMethodType;
    }

    @Override
    public ModelAndView invoke(InvokeData data) {
        if (!agreedWithRequestType(data.getReqType())) {
            return null;
        }

        String path = data.getRequest().getPathInfo();
        if ((path == null) || (path.length() == 0)) {
            path = data.getRequest().getServletPath();
        }

        Matcher m = pathPattern.matcher(path);
        if (!m.find()) {
            return null;
        }


        InvokeData mdata = new InvokeData(m, data);
        ModelAndView res = decoratedInvoker.invoke(mdata);
        return res;
    }

    private boolean agreedWithRequestType(HttpMethodType reqType) {
        return (this.requestTypeFilter == HttpMethodType.ALL || this.requestTypeFilter == reqType);
    }

    /*----------------------------------------------------------------------*/
}
