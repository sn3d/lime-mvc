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
package org.zdevra.guice.mvc.views;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zdevra.guice.mvc.ModelMap;
import org.zdevra.guice.mvc.ViewPoint;
import org.zdevra.guice.mvc.exceptions.ViewPointException;

/**
 * Viewpoint that do redirection to the URL.All request's attributes are posted
 * as parameters. ContextRelative flag determines whether URL is relative to
 * server or is relative to servlet path. Default value is true what means the
 * relative path will be placed after 'http://server:port/appcontext/servlet/'.
 */
public class RedirectViewPoint implements ViewPoint {

    //-----------------------------------------------------------------------------------------------------------
    // m. variables
    //-----------------------------------------------------------------------------------------------------------
    private final String url;
    private final boolean contextRelative;
    private final boolean isAbsolute;

    //-----------------------------------------------------------------------------------------------------------
    // constructor
    //-----------------------------------------------------------------------------------------------------------
    /**
     * Constructor
     *
     * @param url
     */
    public RedirectViewPoint(String url) {
        this(url, true);
    }

    /**
     * Constructor
     *
     * @param url
     */
    public RedirectViewPoint(String url, boolean contextRelative) {
        this.url = url;

        if (url.startsWith("/")) {
            this.isAbsolute = false;
        } else {
            this.isAbsolute = true;
        }

        this.contextRelative = contextRelative;
    }

    //-----------------------------------------------------------------------------------------------------------
    // methods
    //-----------------------------------------------------------------------------------------------------------
    @Override
    public void render(ModelMap model, HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) {
        try {
            String redirectUrl = generateRedirectUrl(model, request);
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            throw new ViewPointException("Redirection fails", request, e);
        }
    }

    /**
     * Take base url and construct the final redirection with parameters.
     *
     * @param request
     * @return
     */
    private String generateRedirectUrl(ModelMap model, HttpServletRequest request) {
        StringBuilder redirectUrl = null;
        if (isAbsolute) {
            redirectUrl = new StringBuilder(this.url);
        } else if (contextRelative) {
            String ctxPath = request.getContextPath();
            String servletPath = request.getServletPath();
            redirectUrl = new StringBuilder(ctxPath + servletPath + this.url);
        } else {
            redirectUrl = new StringBuilder(this.url);
        }


        boolean isFirst = true;
        Iterator<Entry<String, Object>> it = model.entrySet().iterator();

        while (it.hasNext()) {
            Entry<String, Object> pair = it.next();

            if (isFirst) {
                redirectUrl.append("?");
                isFirst = false;
            } else {
                redirectUrl.append("&");
            }

            redirectUrl.append(pair.getKey());
            redirectUrl.append("=");
            redirectUrl.append(pair.getValue().toString());

        }

        return redirectUrl.toString();
    }
}
