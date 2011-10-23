package org.zdevra.guice.mvc.case7;

import org.zdevra.guice.mvc.ConversionService;
import org.zdevra.guice.mvc.converters.AbstractConverter;

import java.lang.annotation.Annotation;
import java.util.Map;

public class CustomConverterFactory implements ConversionService.ConverterFactory
{
    @Override
    public ConversionService.Converter<?> createConverter(Class<?> type, Annotation[] annotations) {
        if (type == Case7Test.class) {
            return new CustomConverter();
        }
        return null;
    }

    private static class CustomConverter extends AbstractConverter<Integer> {
        @Override
        public Integer convert(String name, Map<String, String[]> data) {
            return null;
        }
    }
}
