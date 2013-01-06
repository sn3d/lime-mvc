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
package org.zdevra.guice.mvc.case6;

import org.zdevra.guice.mvc.TestServlet;

public class Case6Servlet extends TestServlet {

    public Case6Servlet() {
        super(
                new Class<?>[]{
                    Case6ControllerCars.class,
                    Case6ControllerPeople.class
                },
                new Case6Module());
    }
}
