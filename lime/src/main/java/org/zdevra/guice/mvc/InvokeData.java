/**
 * ***************************************************************************
 * Copyright 2011 Zdenko Vrabel
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 ****************************************************************************
 */
package org.zdevra.guice.mvc;

import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Injector;

/**
 * The class collects all data which may be important for one invocation of the
 * method.
 */
public class InvokeData {
    /*---------------------------- m. variables ----------------------------*/

    private final Matcher uriMatcher;
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final ModelMap model;
    private final HttpMethodType reqType;
    private final Injector injector;

    /*---------------------------- constructors ----------------------------*/
    public InvokeData(HttpServletRequest request, HttpServletResponse response, ModelMap model, HttpMethodType reqType, Injector injector) {
        this.request = request;
        this.response = response;
        this.model = model;
        this.reqType = reqType;
        this.injector = injector;
        this.uriMatcher = null;
    }

    public InvokeData(Matcher uriMatcher, InvokeData copy) {
        this.uriMatcher = uriMatcher;
        this.request = copy.request;
        this.response = copy.response;
        this.model = copy.model;
        this.reqType = copy.reqType;
        this.injector = copy.injector;
    }

    public InvokeData(ModelMap m, InvokeData copy) {
        this.model = m;
        this.uriMatcher = copy.uriMatcher;
        this.request = copy.request;
        this.response = copy.response;
        this.reqType = copy.reqType;
        this.injector = copy.injector;
    }

    /*-------------------------- getters/setters ---------------------------*/
    public Matcher getUriMatcher() {
        return uriMatcher;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public ModelMap getModel() {
        return model;
    }

    public HttpMethodType getReqType() {
        return reqType;
    }

    public Injector getInjector() {
        return injector;
    }
    /*----------------------------------------------------------------------*/
}
