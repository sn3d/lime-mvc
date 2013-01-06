/**
 * ***************************************************************************
 * Copyright 2012 Zdenko Vrabel
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

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;

/**
 * Servlet provide direct view rendering without any controlling.
 *
 */
class ViewServlet extends HttpServlet {

// ------------------------------------------------------------------------
    private final ViewPoint view;
    private ViewResolver viewResolver;
    private ExceptionResolver exceptionResolver;

// ------------------------------------------------------------------------
    public ViewServlet(ViewPoint view) {
        this.view = view;
    }

// ------------------------------------------------------------------------
    @Inject
    public final void setViewResolver(ViewResolver viewResolver) {
        this.viewResolver = viewResolver;
    }

    @Inject
    public final void setExceptionResolver(ExceptionResolver exceptionResolver) {
        this.exceptionResolver = exceptionResolver;
    }

    private void redirectToView(HttpServletRequest req, HttpServletResponse resp) {
        try {
            viewResolver.resolve(view, new ModelMap(), this, req, resp);
        } catch (Throwable e) {
            exceptionResolver.handleException(e, this, req, resp);
        }
    }

// ------------------------------------------------------------------------
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.redirectToView(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.redirectToView(req, resp);
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.redirectToView(req, resp);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.redirectToView(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.redirectToView(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.redirectToView(req, resp);
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.redirectToView(req, resp);
    }
// ------------------------------------------------------------------------
}
