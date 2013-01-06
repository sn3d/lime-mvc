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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.zdevra.guice.mvc.ConversionService.Converter;

/**
 * Converter provide conversion from request to the array of simple types as integer,
 * long, enumerations. This converter is not suitable for complex objects with multiple
 * values.
 */
public abstract class ArrayConverter<T> implements Converter<T[]> {

// ------------------------------------------------------------------------
    private final T[] empty;

// ------------------------------------------------------------------------
    protected abstract T convertItem(String value);

// ------------------------------------------------------------------------
    public ArrayConverter(T[] emptyArray) {
        this.empty = emptyArray;
    }

    @Override
    public final T[] convert(String name, Map<String, String[]> data) {
        String[] values = data.get(name);
        if (values == null) {
            return empty;
        }

        List<T> out = new LinkedList<T>();
        for (int i = 0; i < values.length; ++i) {
            T itm = convertItem(values[i]);
            if (itm != null) {
                out.add(itm);
            }
        }
        return out.toArray(empty);
    }
// ------------------------------------------------------------------------
}
