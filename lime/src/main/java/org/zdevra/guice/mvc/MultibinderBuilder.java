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
package org.zdevra.guice.mvc;

import com.google.inject.Binder;
import com.google.inject.multibindings.Multibinder;

class MultibinderBuilder<T> {

// ------------------------------------------------------------------------
    protected final Binder binder;
    protected final Multibinder<T> multibinder;

// ------------------------------------------------------------------------
    /**
     * Constructor
     */
    public MultibinderBuilder(Binder binder, Class<T> clazz) {
        this.binder = binder;
        this.multibinder = Multibinder.newSetBinder(binder, clazz);
    }

    public void registerClass(Class<? extends T> clazz) {
        multibinder.addBinding().to(clazz).asEagerSingleton();
    }

    public void registerInstance(T instance) {
        binder.requestInjection(instance);
        multibinder.addBinding().toInstance(instance);
    }
// ------------------------------------------------------------------------
}
