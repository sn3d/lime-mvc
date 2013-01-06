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
package org.zdevra.guice.mvc.converters;

import java.util.Map;

import org.zdevra.guice.mvc.ConversionService.Converter;

/**
 * Abstract class simplify conversion of simple types like integer, float etc
 *
 * @param <T>
 */
public abstract class TypeConverter<T> implements Converter<T> {

// ------------------------------------------------------------------------
    /**
     * Implements your own conversion method
     */
    protected abstract T convertType(String stringValue);

// ------------------------------------------------------------------------
    /**
     * Constructor
     *
     * @param clazz
     */
    public TypeConverter() {
    }

// ------------------------------------------------------------------------
    @Override
    public final T convert(String name, Map<String, String[]> data) {
        String[] values = data.get(name);
        if ((values != null) && (values.length > 0)) {
            return convertType(values[0]);
        } else {
            return convertType("");
        }
    }
// ------------------------------------------------------------------------
}
