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
package org.zdevra.guice.mvc.converters;

import org.zdevra.guice.mvc.ConversionService;

import java.lang.annotation.Annotation;

/**
 * The purpose of the dummy class is having some static empty factory for default parameter
 * in annotation {@link org.zdevra.guice.mvc.annotations.RequestParameter}.
 *
 * I need to somehow detect when is converterFactory in RequestParameter defined and when doesn't.
 */
public class NoConverterFactory implements ConversionService.ConverterFactory {

    @Override
    public ConversionService.Converter<?> createConverter(Class<?> type, Annotation[] annotations) {
        return null;
    }
}
