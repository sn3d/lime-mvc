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
package org.zdevra.guice.mvc.casePerformance;

import java.io.IOException;

import javax.servlet.ServletException;

import org.testng.annotations.Test;
import org.zdevra.guice.mvc.AbstractTest;

/**
 * This is simple performance test. I'm using this test for compare and identify
 * how new changes degraded a performance.
 */
@Test
public class PerfTest extends AbstractTest {

    private final static int COUNT = 1000;

    public PerfTest() {
        super(PerfServlet.class);
    }

    @Test
    public void testAllArticlesNamed() throws IOException, ServletException {
        for (int i = 0; i < COUNT; ++i) {
            for (int x = 0; x < 5; ++x) {
                executeSimpleUrl(BASE_URL + "/blog/allarticles/namedview");
            }
        }
    }

    @Test
    public void testAllArticlesDirect() throws IOException, ServletException {
        for (int i = 0; i < COUNT; ++i) {
            for (int x = 0; x < 5; ++x) {
                executeSimpleUrl(BASE_URL + "/blog/allarticles/directview");
            }
        }
    }

    @Test
    public void testArticleDetailNamed() throws IOException, ServletException {
        for (int i = 0; i < COUNT; ++i) {
            executeSimpleUrl(BASE_URL + "/blog/article/1/namedview");
            executeSimpleUrl(BASE_URL + "/blog/article/2/namedview");
            executeSimpleUrl(BASE_URL + "/blog/article/3/namedview");
            executeSimpleUrl(BASE_URL + "/blog/article/4/namedview");
            executeSimpleUrl(BASE_URL + "/blog/article/5/namedview");
        }
    }

    @Test
    public void testArticleDetailDirect() throws IOException, ServletException {
        for (int i = 0; i < COUNT; ++i) {
            executeSimpleUrl(BASE_URL + "/blog/article/1/directview");
            executeSimpleUrl(BASE_URL + "/blog/article/2/directview");
            executeSimpleUrl(BASE_URL + "/blog/article/3/directview");
            executeSimpleUrl(BASE_URL + "/blog/article/4/directview");
            executeSimpleUrl(BASE_URL + "/blog/article/5/directview");
        }
    }
}
