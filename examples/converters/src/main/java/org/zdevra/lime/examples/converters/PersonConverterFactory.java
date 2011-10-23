package org.zdevra.lime.examples.converters;


import org.zdevra.guice.mvc.ConversionService;
import org.zdevra.guice.mvc.converters.AbstractConverter;
import org.zdevra.guice.mvc.converters.ArrayConverter;
import org.zdevra.guice.mvc.converters.TypeConverter;

import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PersonConverterFactory implements ConversionService.ConverterFactory {

// ------------------------------------------------------------------------

    private final ConversionService.Converter<? extends Person> converter = new PersonConverter();

// ------------------------------------------------------------------------

    public ConversionService.Converter<?> createConverter(Class<?> type, Annotation[] annotations) {
        if (type == Person.class) {
            return converter;
        }
        return null;
    }

// ------------------------------------------------------------------------

    private static class PersonConverter implements ConversionService.Converter<Person> {

        public Person convert(String nameInForm, Map<String, String[]> data)
        {
            String personName    = getValue(nameInForm + "-name", data);
            String personSurname = getValue(nameInForm + "-surname", data);
            String personEmail   = getValue(nameInForm + "-email", data);
            int    personAge     = Integer.parseInt(getValue(nameInForm + "-age", data));
            return new Person(personName, personSurname, personEmail, personAge);
        }

        private final static String getValue(String name, Map<String, String[]> data)
        {
            String[] values = data.get(name);
            if ((values == null) || (values.length == 0)) {
                return "";
            }
            return values[0];
        }
    }

    private static class Test extends AbstractConverter<Person> {

        public Person convert(String nameInForm, Map<String, String[]> data) {
            String personName    = getValue(nameInForm + "-name", data);
            String personSurname = getValue(nameInForm + "-surname", data);
            String personEmail   = getValue(nameInForm + "-email", data);
            int    personAge     = getValueInt(nameInForm + "-age", data);
            return new Person(personName, personSurname, personEmail, personAge);

        }
    }

// ------------------------------------------------------------------------

}
