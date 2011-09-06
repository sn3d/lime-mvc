package org.zdevra.guice.mvc.converters;

import org.zdevra.guice.mvc.ConversionService;

import java.lang.annotation.Annotation;
import java.util.Map;

public class StringConverterFactory implements ConversionService.ConverterFactory {

// ------------------------------------------------------------------------

    private final ConversionService.Converter<String> stringConverter = new StringConverter();
    private final ConversionService.Converter<String[]> stringArrayConverter = new StringArrayConverter();

// ------------------------------------------------------------------------

    @Override
    public ConversionService.Converter<?> createConvertor(Class<?> type, Annotation[] annotations) {
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
