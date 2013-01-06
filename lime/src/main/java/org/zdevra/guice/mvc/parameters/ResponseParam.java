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

import javax.servlet.http.HttpServletResponse;

import org.zdevra.guice.mvc.InvokeData;

/**
 * The parameter's processor is executed when the method has a parameter, which
 * is a instance of HttpResponseRequest, and put the response object into this
 * parameter. <p>
 *
 * for example: <pre class="prettyprint"> {@literal @}Path("/control") public
 * void controllerMethod(HttpServletResponse response) { ... }
 * </pre>
 */
public class ResponseParam implements ParamProcessor {

    /*----------------------------------------------------------------------*/
    /**
     * Factory class for {@link ResponseParam}
     */
    public static class Factory implements ParamProcessorFactory {

        private ParamProcessor processor = new ResponseParam();

        @Override
        public ParamProcessor buildParamProcessor(ParamMetadata metadata) {
            if (metadata.getType() != HttpServletResponse.class) {
                return null;
            }
            return processor;
        }
    }

    /*----------------------------------------------------------------------*/
    /**
     * Constructor
     */
    private ResponseParam() {
    }

    @Override
    public Object getValue(InvokeData data) {
        return data.getResponse();
    }

    /*----------------------------------------------------------------------*/
}
