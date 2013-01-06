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

import com.google.inject.Injector;

/**
 * This binding class bind an exception to handler's class. A handler lifecycle
 * is under Guice's control.
 *
 * @see ExceptionHandler
 * @see ExceptionBind
 * @see GuiceExceptionResolver
 */
class ExceptionBindToClass extends ExceptionBind {

// ------------------------------------------------------------------------
    private final Class<? extends ExceptionHandler> handlerClass;

// ------------------------------------------------------------------------
    public ExceptionBindToClass(Class<? extends ExceptionHandler> handlerClass, Class<? extends Throwable> exceptionClass, int order) {
        super(exceptionClass, order);
        this.handlerClass = handlerClass;
    }

// ------------------------------------------------------------------------
    @Override
    public ExceptionHandler getHandler(Injector injector) {
        return injector.getInstance(handlerClass);
    }
// ------------------------------------------------------------------------
}
