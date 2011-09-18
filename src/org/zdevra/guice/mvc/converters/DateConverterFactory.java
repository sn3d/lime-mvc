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
import org.zdevra.guice.mvc.Utils;
import org.zdevra.guice.mvc.exceptions.IllegalConversionException;

import java.lang.annotation.Annotation;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The class provide converter which converts a string value to the Date
 */
public class DateConverterFactory implements ConversionService.ConverterFactory {

// ------------------------------------------------------------------------

    @Override
    public ConversionService.Converter<?> createConvertor(Class<?> type, Annotation[] annotations) {
        if (type != Date.class) {
            return null;
        }

        DateConv da = Utils.getAnnotation(DateConv.class, annotations);
        DateFormat df = new SimpleDateFormat(da.value());

        try {
            return new DateConverter(df, df.parse(da.defaultValue()));
        } catch (ParseException e) {
            return new DateConverter(df);
        }
    }

// ------------------------------------------------------------------------

    private static class DateConverter extends TypeConverter<Date> {

        private final DateFormat dateFormat;
        private final Date defaultDate;

        DateConverter(DateFormat df) {
            this(df, null);
        }

        DateConverter(DateFormat df, Date defaultDate) {
            this.dateFormat = df;
            this.defaultDate = defaultDate;
        }

        @Override
        protected Date convertType(String stringValue) {
            try {
                return this.dateFormat.parse(stringValue);
            } catch (Exception e) {
                if (this.defaultDate != null) {
                    return defaultDate;
                }
                throw new IllegalConversionException("A conversion from the '" + stringValue + "' to the date '" + this.dateFormat.toString() + "' failed");
            }
        }
    }

// ------------------------------------------------------------------------
}
