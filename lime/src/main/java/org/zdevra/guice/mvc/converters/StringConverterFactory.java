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
package org.zdevra.guice.mvc.converters;

import java.lang.annotation.Annotation;

import org.zdevra.guice.mvc.ConversionService;

public class StringConverterFactory implements ConversionService.ConverterFactory {

// ------------------------------------------------------------------------
    private final ConversionService.Converter<String> stringConverter = new StringConverter();
    private final ConversionService.Converter<String[]> stringArrayConverter = new StringArrayConverter();

// ------------------------------------------------------------------------
    @Override
    public ConversionService.Converter<?> createConverter(Class<?> type, Annotation[] annotations) {
        if (type == String.class) {
            return stringConverter;
        } else if (type == String[].class) {
            return stringArrayConverter;
        }
        return null;
    }

// ------------------------------------------------------------------------
    private static class StringConverter extends TypeConverter<String> {

        @Override
        protected String convertType(String stringValue) {
            return stringValue;
        }
    }

    private static class StringArrayConverter extends ArrayConverter<String> {

        private StringArrayConverter() {
            super(new String[]{});
        }

        @Override
        protected String convertItem(String value) {
            return value;
        }
    }
// ------------------------------------------------------------------------
}
