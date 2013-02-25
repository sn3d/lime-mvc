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

import org.zdevra.guice.mvc.MvcModule.ExceptionResolverBindingBuilder;
import org.zdevra.guice.mvc.views.NamedView;

import com.google.inject.Binder;

/** 
 * The builder is responsible for building a bindings
 * between exceptions and exception handlers for
 * {@link GuiceExceptionResolver}
 * This builder is used internally by {@link MvcModule}.
 * 
 * @see MvcModule
 * @see GuiceExceptionResolver
 */
class ExceptionResolverBuilder extends MultibinderBuilder<ExceptionBind> {

    /** the number is incremented for each exception handler */
    private int orderIndex;

    /**
     * Constructor
     * @param binder
     */
    public ExceptionResolverBuilder(Binder binder) {
        super(binder, ExceptionBind.class);
        this.orderIndex = 0;
    }

    public ExceptionResolverBindingBuilder bindException(Class<? extends Throwable> exceptionClass) {
        return new ExceptionResolverBindBuilderImpl(exceptionClass);
    }

    /**
     * nested implementation of {@link ExceptionResolverBindingBuilder builder}
     */
    public class ExceptionResolverBindBuilderImpl implements ExceptionResolverBindingBuilder {

        private final Class<? extends Throwable> exceptionClass;

        public ExceptionResolverBindBuilderImpl(Class<? extends Throwable> exceptionClass) {
            this.exceptionClass = exceptionClass;
        }

        @Override
        public void toHandler(Class<? extends ExceptionHandler> handlerClass) {
            registerInstance(ExceptionBind.toClass(handlerClass, exceptionClass, orderIndex));
            orderIndex++;
        }

        @Override
        public void toHandlerInstance(ExceptionHandler handler) {
            ExceptionBind binding = ExceptionBind.toInstance(handler, exceptionClass, orderIndex);
            registerInstance(binding);
            orderIndex++;
        }

        @Override
        public void toErrorView(String viewName) {
            toErrorView(NamedView.create(viewName));
        }

        @Override
        public void toErrorView(ViewPoint errorView) {
            ExceptionHandler handler = new ViewExceptionHandler(errorView);
            binder.requestInjection(errorView);
            binder.requestInjection(handler);
            ExceptionBind exceptionBind = ExceptionBind.toInstance(handler, exceptionClass, orderIndex);
            registerInstance(exceptionBind);
            orderIndex++;
        }
    }
}
