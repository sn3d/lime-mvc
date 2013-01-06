/*****************************************************************************
 * Copyright 2012 Zdenko Vrabel
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
package org.zdevra.guice.mvc.parameters;

import javax.servlet.http.HttpSession;

import org.zdevra.guice.mvc.InvokeData;

/**
 * The parameter's processor forwards the session object into the method's parameter. 
 * <p>
 * 
 * <pre class="prettyprint">
 *  {@literal @}Path("/somepath") 
 *  public void getInfoAboutLoggedUser(HttpSession session) {
 *    ...			
 *	}
 * </pre>
 * <p>
 *
 */
public class HttpSessionParam implements ParamProcessor {

    /*----------------------------------------------------------------------*/
    public static class Factory implements ParamProcessorFactory {

        private final ParamProcessor processor = new HttpSessionParam();

        @Override
        public ParamProcessor buildParamProcessor(ParamMetadata metadata) {
            if (metadata.getType() != HttpSession.class) {
                return null;
            }
            return processor;
        }
    }

    /*---------------------------- constructors ----------------------------*/
    /**
     * Constructor
     */
    private HttpSessionParam() {
    }

    @Override
    public Object getValue(InvokeData data) {
        HttpSession session = data.getRequest().getSession();
        return session;
    }

    /*----------------------------------------------------------------------*/
}
