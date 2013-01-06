/**
 * ***************************************************************************
 * Copyright 2012 Zdenko Vrabel
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
package org.zdevra.guice.mvc.parameters;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.inject.Inject;

import org.zdevra.guice.mvc.InvokeData;

import com.google.inject.Key;
import com.google.inject.name.Named;

/**
 * The parameter's processor pick up the instance from Guice and forward the
 * instance to the method's parameter.
 *
 * Method must be annotated with {@literal @}Inject annocation. The
 * {@literal @}Named annotation for the method's parameters is supported as
 * well.
 *
 */
public class InjectorParam implements ParamProcessor {
    /*---------------------------- m. variables ----------------------------*/

    private final Key<?> key;

    /*----------------------------------------------------------------------*/
    /**
     * Factory class for {@link InjectorParam}
     */
    public static class Factory implements ParamProcessorFactory {

        @Override
        public ParamProcessor buildParamProcessor(ParamMetadata metadata) {
            if (isInjectAnnotated(metadata.getMethod())) {
                Annotation namedAnnotation = metadata.getAnnotation(Named.class);
                if (namedAnnotation == null) {
                    namedAnnotation = metadata.getAnnotation(javax.inject.Named.class);
                }
                return new InjectorParam(metadata.getType(), namedAnnotation);
            }
            return null;
        }

        private boolean isInjectAnnotated(Method method) {
            Annotation inject = method.getAnnotation(Inject.class);
            if (inject != null) {
                return true;
            }
            return false;
        }
    }

    /*----------------------------------------------------------------------*/
    /**
     * Constructor
     */
    private InjectorParam(Class<?> type, Annotation annotation) {
        if (annotation == null) {
            this.key = Key.get(type);
        } else {
            this.key = Key.get(type, annotation);
        }
    }

    @Override
    public Object getValue(InvokeData data) {
        Object instance = data.getInjector().getInstance(key);
        return instance;
    }
    /*----------------------------------------------------------------------*/
}
