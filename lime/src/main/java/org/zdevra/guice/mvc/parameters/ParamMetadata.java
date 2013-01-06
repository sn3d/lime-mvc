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
package org.zdevra.guice.mvc.parameters;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.zdevra.guice.mvc.ConversionService;
import org.zdevra.guice.mvc.Utils;

/**
 * The class is immutable collection of all method's parameter metadata like
 * annotations, type, parameter builder, etc.
 *
 * This metadata object is consumed by the parameter processor factory and the
 * factory constructs parameter processor from metadata.
 *
 */
public class ParamMetadata {
    /*---------------------------- m. variables ----------------------------*/

    private final Method method;
    private final Class<?> type;
    private final Annotation[] annotations;
    private final ConversionService conversionService;

    /*---------------------------- constructors ----------------------------*/
    public ParamMetadata(Class<?> type, Annotation[] annotations, ConversionService conversionService, Method method) {
        super();
        this.type = type;
        this.annotations = annotations;
        this.conversionService = conversionService;
        this.method = method;
    }

    /*-------------------------- getters/setters ---------------------------*/
    public Class<?> getType() {
        return type;
    }

    public Annotation[] getAnnotations() {
        return annotations;
    }

    public <T extends Annotation> T getAnnotation(Class<T> annotationClazz) {
        return Utils.getAnnotation(annotationClazz, this.annotations);
    }

    public ConversionService getConversionService() {
        return conversionService;
    }

    public Method getMethod() {
        return method;
    }

    /*----------------------------------------------------------------------*/
}
