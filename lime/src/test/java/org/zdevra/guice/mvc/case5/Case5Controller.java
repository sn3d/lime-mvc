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
package org.zdevra.guice.mvc.case5;

import org.zdevra.guice.mvc.annotations.Controller;
import org.zdevra.guice.mvc.annotations.Model;
import org.zdevra.guice.mvc.annotations.Path;
import org.zdevra.guice.mvc.annotations.View;

@Controller
@View("one.jsp")
public class Case5Controller {

    @Path("/action/one")
    @Model("testmsg")
    public String actionOne() {
        return "onedata";
    }

    @Path("/action/two")
    @View("two.jsp")
    @Model("testmsg")
    public String actionTwo() {
        return "twodata";
    }

    @Path("/action/three")
    @Model("testmsg")
    @View("three.jsp")
    public String actionThree() {
        return "threedata";
    }

    @Path("/action/custom")
    @Model("testmsg")
    @ToTestView
    public String actionCustom() {
        return "customdata";
    }
}
