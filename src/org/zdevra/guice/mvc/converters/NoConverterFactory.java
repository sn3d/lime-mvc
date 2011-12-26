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
