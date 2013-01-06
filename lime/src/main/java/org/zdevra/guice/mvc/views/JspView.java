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
package org.zdevra.guice.mvc.views;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zdevra.guice.mvc.ModelMap;
import org.zdevra.guice.mvc.ViewPoint;
import org.zdevra.guice.mvc.exceptions.InvalidJspViewException;

/**
 * The JSP view redirecting request to concrete JSP or HTML page
 */
public class JspView implements ViewPoint {

// ------------------------------------------------------------------------	
    private static final Logger logger = Logger.getLogger(JspView.class.getName());
    private final String jspPath;

// ------------------------------------------------------------------------
    public JspView(String jspPath) {
        if (jspPath == null || jspPath.length() == 0) {
            this.jspPath = "";
        } else if (!jspPath.startsWith("/")) {
            this.jspPath = "/" + jspPath;
        } else {
            this.jspPath = jspPath;
        }
    }

// ------------------------------------------------------------------------
    @Override
    public void render(ModelMap model, HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        request = new HttpRequestForForward(request, jspPath);
        RequestDispatcher dispatcher = servlet.getServletContext().getRequestDispatcher(jspPath);
        if (dispatcher == null) {
            throw new InvalidJspViewException(jspPath);
        }

        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            logger.log(Level.SEVERE, "Error in JspView", e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error in JspView", e);
        }

        request = null;
    }

    @Override
    public String toString() {
        return "JspView [jsp=" + jspPath + "]";
    }
// ------------------------------------------------------------------------
}
