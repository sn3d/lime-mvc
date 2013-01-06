/*****************************************************************************
 * Copyright 2011 Zdenko Vrabel
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

import com.google.inject.Injector;

/**
 * Class is representing bind/relation between an exception class and exception handler.
 * Binding are used by {@link DefaultExceptionResolver}.
 * 
 * Bindings are constructed via static factory methods toClass() and toInstance().
 * There are 2 implementations of the binding. {@link ExceptionBindToClass} and
 * {@link ExceptionBindToInstance} 
 * 
 * 
 * @see GuiceExceptionResolver
 * @see ExceptionBindToClass
 * @see ExceptionBindToInstance
 * @see ExceptionHandler
 */
abstract class ExceptionBind implements Comparable<ExceptionBind> {

    protected final Class<? extends Throwable> exceptionClass;
    protected final int order;

    /**
     * Method constructs binding for exception to handler's class.
     * 
     * @param handlerClass
     * @param exteption
     * @param order exception handling is executed in order. Handlers are
     *        first ordered in ascending order by this order index.
     *         
     * @return instance of binding
     */
    public static ExceptionBind toClass(Class<? extends ExceptionHandler> handlerClass, Class<? extends Throwable> exception, int order) {
        return new ExceptionBindToClass(handlerClass, exception, order);
    }

    /**
     * Method constructs binding for exception to concrete
     * instance of handler.
     * 
     * @param handler
     * @param exteption
     * @param order exception handling is executed in order. Handlers are
     *        first ordered in ascending order by this order index.
     *         
     * @return instance of binding
     */
    public static ExceptionBind toInstance(ExceptionHandler handler, Class<? extends Throwable> exception, int order) {
        return new ExceptionBindToInstance(handler, exception, order);
    }

    /**
     * Constructor is hidden, binding is constructed
     * via factory static methods.
     * @param exceptionClass
     * @param order
     */
    ExceptionBind(Class<? extends Throwable> exceptionClass, int order) {
        this.exceptionClass = exceptionClass;
        this.order = order;
    }

    public final Class<? extends Throwable> getExceptionClass() {
        return this.exceptionClass;
    }

    @Override
    public int compareTo(ExceptionBind o) {
        if (this.order < o.order) {
            return -1;
        } else if (this.order > o.order) {
            return +1;
        }
        return 0;
    }

    /**
     * Method return instance of exception handler.
     * This depends on child's implementation.
     * 
     * @param injetor
     * @return instance of handler class.
     */
    public abstract ExceptionHandler getHandler(Injector injetor);
}