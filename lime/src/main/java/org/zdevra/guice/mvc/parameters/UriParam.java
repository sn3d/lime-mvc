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
package org.zdevra.guice.mvc.parameters;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import org.zdevra.guice.mvc.ConversionService;
import org.zdevra.guice.mvc.ConversionService.Converter;
import org.zdevra.guice.mvc.annotations.UriParameter;
import org.zdevra.guice.mvc.InvokeData;
import org.zdevra.guice.mvc.Utils;

/**
 * The parameter's parameter processor for
 * {@link org.zdevra.guice.mvc.annotations.UriParameter} annotation.
 *
 * The Url in {@literal @}Path of the method can be a regular expression.
 * {@link org.zdevra.guice.mvc.annotations.UriParameter} pick up the value of
 * some regexp. group and put it into method's parameter. <p>
 *
 * Processor uses the same type conversion like RequestParam processor.
 *
 */
public final class UriParam implements ParamProcessor {

    /*---------------------------- m. variables ----------------------------*/
    private final int group;
    private final Converter<?> converter;

    /*----------------------------------------------------------------------*/
    /**
     * Factory class for {@link UriParam}
     */
    public static class Factory implements ParamProcessorFactory {

        public ParamProcessor buildParamProcessor(ParamMetadata metadata) {
            Annotation[] paramAnnotations = metadata.getAnnotations();
            ConversionService convrtService = metadata.getConversionService();
            Class<?> paramType = metadata.getType();

            UriParameter annotation = Utils.getAnnotation(UriParameter.class, paramAnnotations);
            if (annotation == null) {
                return null;
            }

            //choose converter (explicit defined in annotation or implicit if annotation contains NoConverterFactory value)
            Converter<?> typeConverter = convrtService.getConverter(annotation.converterFactory(), paramType, paramAnnotations);

            return new UriParam(annotation.value(), typeConverter);
        }
    }

    /*----------------------------------------------------------------------*/
    /**
     * Hidden constructor. For the processor's constuction is used Factory
     * class.
     */
    private UriParam(int group, Converter<?> converter) {
        super();
        this.group = group;
        this.converter = converter;
    }

    public Object getValue(InvokeData data) {
        Map<String, String[]> parameters = new HashMap<String, String[]>();
        int groupCount = data.getUriMatcher().groupCount();
        for (int i = 0; i <= groupCount; ++i) {
            parameters.put(Integer.toString(i), new String[]{data.getUriMatcher().group(i)});
        }

        Object convertedValue = converter.convert(Integer.toString(group), parameters);
        return convertedValue;
    }

    /*----------------------------------------------------------------------*/
}
