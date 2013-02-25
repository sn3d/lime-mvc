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

import org.zdevra.guice.mvc.ConversionService;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * The class extends the functionality of the basic converter about methods which gets the
 * values from input data.
 *
 * You don't have to iterate trough the map and array in the data.
 */
public abstract class AbstractConverter<T> implements ConversionService.Converter<T> {

    /**
     * Short version of the getValue method which returns value on 0 position
     *
     * @param name
     * @param data
     * @return
     */
    protected final static String getValue(String name, Map<String, String[]> data) {
        return getValue(name, 0, data);
    }

    /**
     * Short version of the getValueInt method which returns value on 0 position
     *
     * @param name
     * @param data
     * @return
     */
    protected final static int getValueInt(String name, Map<String, String[]> data) {
        return getValueInt(name, 0, data);
    }

    /**
     * Method extract the value as from the incoming data a String
     *
     * @param name
     * @param data
     * @param index
     *
     * @return
     */
    protected final static String getValue(String name, int index, Map<String, String[]> data) {
        String[] values = data.get(name);
        if ((values == null) || (values.length == 0)) {
            return "";
        }
        return values[index];
    }

    /**
     * Method extract the value from the incoming data as a Int
     *
     * @param name
     * @param data
     * @param index
     *
     * @return
     */
    protected final static int getValueInt(String name, int index, Map<String, String[]> data) {
        String value = getValue(name, index, data);
        return Integer.parseInt(value);
    }

    /**
     * Method extract the value from the incoming data as a Long
     *
     * @param name
     * @param data
     * @param index
     *
     * @return
     */
    protected final static long getValueLong(String name, int index, Map<String, String[]> data) {
        String value = getValue(name, index, data);
        return Long.parseLong(value);
    }

    /**
     * Method extract the value from the incoming data as a Double
     *
     * @param name
     * @param data
     * @param index
     *
     * @return
     */
    protected final static double getValueDouble(String name, int index, Map<String, String[]> data) {
        String value = getValue(name, index, data);
        return Double.parseDouble(value);
    }

    /**
     * Method extract the value from the incoming data as a Date
     *
     * @param name
     * @param data
     * @param index
     * @param df
     * @return
     */
    protected final static Date getValueDate(String name, int index, DateFormat df, Map<String, String[]> data) {
        try {
            String value = getValue(name, index, data);
            return df.parse(value);
        } catch (ParseException e) {
            return new Date(0);
        }
    }
}
