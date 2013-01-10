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
package org.zdevra.guice.mvc.exceptions;

/**
 * Exception is throw when the controller select
 * view name but in the Mvc Module is no associated view
 * to this name.
 */
public class NoViewForNameException extends MvcException {

    public NoViewForNameException(String viewName) {
        super("No view is associated to the view name '" + viewName + "'");
    }
}
