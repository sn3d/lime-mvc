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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.zdevra.guice.mvc.exceptions.InvalidMethodParameterException;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * This class collect the all registered processor factories for parameters
 * which can be used and also the class choose the proper-one for the parameter.
 *
 *
 * @see ParamProcessorFactory
 * @see ParamProcessor
 */
@Singleton
public class ParamProcessorsService {
    /*---------------------------- m. variables ----------------------------*/

    private final Collection<ParamProcessorFactory> factories;

    /*---------------------------- constructors ----------------------------*/
    @Inject
    public ParamProcessorsService(Set<ParamProcessorFactory> factories) {
        this.factories = new ArrayList<ParamProcessorFactory>(factories);
    }

    /*------------------------------- methods ------------------------------*/
    public ParamProcessor createProcessor(ParamMetadata metadata) {
        for (ParamProcessorFactory factory : factories) {
            ParamProcessor processor = factory.buildParamProcessor(metadata);
            if (processor != null) {
                return processor;
            }
        }

        throw new InvalidMethodParameterException(metadata);
    }

    /*----------------------------------------------------------------------*/
}
