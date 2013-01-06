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

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.testng.annotations.BeforeClass;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.InvocationContext;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

/**
 * All case tests are extending this basic class. The class prepare the Lime MVC
 * module and register the Lime MVC servlet dispatcher as well.
 *
 */
public class AbstractTest {

    public static final String BASE_URL = "http://www.test.com/test";
    private final String servletName;
    private final String servletClassName;
    protected ServletRunner sr;

    public AbstractTest(Class<? extends MvcDispatcherServlet> servletClass, String servletName) {
        this.servletClassName = servletClass.getName();
        this.servletName = servletName;
    }

    public AbstractTest(Class<? extends MvcDispatcherServlet> servletClass) {
        this(servletClass, "test/*");
    }

    @BeforeClass
    public void prepare() {
        sr = new ServletRunner();
        sr.registerServlet(servletName, servletClassName);
    }

    public WebResponse executeSimpleUrl(String url) throws ServletException, IOException {
        //prepare request
        ServletUnitClient sc = sr.newClient();
        WebRequest request = new GetMethodWebRequest(url);

        //invoke request
        InvocationContext ic = sc.newInvocation(request);
        Servlet ss = ic.getServlet();
        ss.service(ic.getRequest(), ic.getResponse());
        WebResponse response = ic.getServletResponse();

        return response;
    }

    public WebResponse executeFormularUrl(String url, Map<String, String[]> data) throws ServletException, IOException {
        //prepare request
        ServletUnitClient sc = sr.newClient();
        WebRequest request = new GetMethodWebRequest(url);
        for (Entry<String, String[]> entry : data.entrySet()) {
            request.setParameter(entry.getKey(), entry.getValue());
        }

        //invoke request
        InvocationContext ic = sc.newInvocation(request);
        Servlet ss = ic.getServlet();
        ss.service(ic.getRequest(), ic.getResponse());
        WebResponse response = ic.getServletResponse();

        return response;
    }
}
