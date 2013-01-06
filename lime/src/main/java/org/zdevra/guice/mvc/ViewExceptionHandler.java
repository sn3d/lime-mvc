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

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;

/**
 * The handler is rendering a view page for the exception.
 */
class ViewExceptionHandler implements ExceptionHandler {

// ------------------------------------------------------------------------
    private static final Logger logger = Logger.getLogger(ViewExceptionHandler.class.getName());
    private final ViewPoint errorView;
    @Inject
    private ViewResolver viewResolver;

// ------------------------------------------------------------------------
    public ViewExceptionHandler(ViewPoint errorView) {
        this.errorView = errorView;
    }

// ------------------------------------------------------------------------
    @Override
    public void handleException(Throwable t, HttpServlet servlet, HttpServletRequest req, HttpServletResponse resp) {
        logger.log(Level.SEVERE, "Exception caught (" + t.getClass().getName() + ")", t);
        viewResolver.resolve(errorView, null, servlet, req, resp);
    }
// ------------------------------------------------------------------------
}
