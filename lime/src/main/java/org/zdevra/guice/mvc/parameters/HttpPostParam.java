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
import java.util.Map;

import org.zdevra.guice.mvc.ConversionService;
import org.zdevra.guice.mvc.ConversionService.Converter;
import org.zdevra.guice.mvc.InvokeData;
import org.zdevra.guice.mvc.Utils;
import org.zdevra.guice.mvc.annotations.RequestParameter;

/**
 * The parameter's processor is executed when method's parameter is annotated by
 * the RequestParameter annotation and take the value from the request's
 * parameter. <p>
 *
 * Example: <pre class="prettyprint"> {@literal @}Path("/control") public void
 * controllerMethod({@literal @}RequestParameter("val1") String val1) { ... }
 * </pre>
 *
 * Example executes the controllerMethod() method when
 * "www.someplace.com/control?val1=somevalue" is requested and puts 'somevalue'
 * into val1 argument. <p>
 *
 * The RequestParam supports arrays as well. Let's assume that we have input
 * fields named like param[0], param[1], param[2] which contains the integer
 * values, we might use the following controlling method:
 *
 * <pre class="prettyprint"> {@literal @}Path("/control") public void
 * controllerMethod({@literal @}RequestParameter("param") int[] values) { ... }
 * </pre>
 *
 *
 * You may explicitly define converter for value. Example below show how to
 * explicitly choose concrete converter (via his factory) <pre
 * class="prettyprint"> {@literal @}Path("/control") public void
 * controllerMethod({@literal @}RequestParameter(value="param",
 * converterFactory=CustomConverterFactory.class) int[] values) { ... }
 * </pre>
 *
 * @see org.zdevra.guice.mvc.annotations.RequestParameter
 */
public class HttpPostParam implements ParamProcessor {
    /*---------------------------- m. variables ----------------------------*/

    private final String requestName;
    private final Converter<?> converter;

    /*----------------------------------------------------------------------*/
    public static class Factory implements ParamProcessorFactory {

        @Override
        public ParamProcessor buildParamProcessor(ParamMetadata metadata) {
            Annotation[] paramAnnotations = metadata.getAnnotations();
            Class<?> paramType = metadata.getType();
            ConversionService convrtService = metadata.getConversionService();

            RequestParameter annotation = Utils.getAnnotation(RequestParameter.class, paramAnnotations);

            if (annotation == null) {
                return null;
            }

            //choose converter (explicit defined in annotation or implicit if annotation contains NoConverterFactory value)
            Converter<?> typeConverter = convrtService.getConverter(annotation.converterFactory(), paramType, paramAnnotations);

            return new HttpPostParam(annotation.value(), typeConverter);
        }
    }

    /*----------------------------------------------------------------------*/
    private HttpPostParam(String requestName, Converter<?> converter) {
        super();
        this.requestName = requestName;
        this.converter = converter;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object getValue(InvokeData data) {
        Map<String, String[]> params = data.getRequest().getParameterMap();
        Object convertedArray = converter.convert(requestName, params);
        return convertedArray;
    }
    /*----------------------------------------------------------------------*/
}
