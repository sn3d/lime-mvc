package org.zdevra.guice.mvc.case8;

import org.zdevra.guice.mvc.ConversionService;
import org.zdevra.guice.mvc.converters.AbstractConverter;

import java.lang.annotation.Annotation;
import java.util.Map;

public class FirstPersonConverterFactory implements ConversionService.ConverterFactory {

    @Override
    public ConversionService.Converter<?> createConverter(Class<?> type, Annotation[] annotations) {
        if (type == Person.class) {
            return new FirstPersonConverter();
        }
        return null;
    }


    private static class FirstPersonConverter extends AbstractConverter<Person> {
        @Override
        public Person convert(String formName, Map<String, String[]> data) {
            String name = "1 " + getValue(formName + "-name", data);
            String surname = "1 " + getValue(formName + "-surname", data);
            return new Person(name, surname);
        }
    }
}
