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
package org.zdevra.guice.mvc.case4;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.zdevra.guice.mvc.AbstractTest;
import org.zdevra.guice.mvc.TestServlet;

import com.meterware.httpunit.WebResponse;

/**
 * This is a testing class for Exception handling mechanism.
 */
@Test
public class Case4Test extends AbstractTest {

    public static class Case4Servlet extends TestServlet {

        public Case4Servlet() {
            super(Case4Controller.class, new Case4Module());
        }
    }

    public Case4Test() {
        super(Case4Servlet.class);
    }

    @Test
    public void testDefaultHandler() throws ServletException, IOException {
        WebResponse resp = executeSimpleUrl("http://www.test.com/test/expetion/npe");
        int code = resp.getResponseCode();
        Assert.assertEquals(code, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void testCustomHandler() throws ServletException, IOException {
        WebResponse resp = executeSimpleUrl("http://www.test.com/test/expetion/custom");
        String out = resp.getText();
        Assert.assertTrue(out.contains("customized handler:CustomException"));
    }

    @Test
    public void testAdvancedCustomHandler() throws ServletException, IOException {
        WebResponse resp = executeSimpleUrl("http://www.test.com/test/expetion/advancedcustom");
        String out = resp.getText();
        Assert.assertTrue(out.contains("customized handler:AdvancedCustomException->CustomException"));
    }

    @Test
    public void testAdvancedHandled() throws ServletException, IOException {
        WebResponse resp = executeSimpleUrl("http://www.test.com/test/expetion/advancedhandledexception");
        String out = resp.getText();
        Assert.assertTrue(out.contains("AdvancedHandledException->CustomException"));
    }

    @Test
    public void testErrorView() throws IOException, ServletException {
        WebResponse resp = executeSimpleUrl("http://www.test.com/test/expetion/errorview");
        String out = resp.getText();
        Assert.assertTrue(out.contains("viewId=errorpage"));
    }
}
