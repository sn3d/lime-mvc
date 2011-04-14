/*****************************************************************************
 * Copyright 2011 Zdenko Vrabel
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

import java.util.LinkedList;
import java.util.List;

/**
 * This class collect the all registered processor factories for parameters
 * which can be used and also the class choose the proper-one for the parameter.
 *  
 */
public class ParamProcessorsService {
/*---------------------------- m. variables ----------------------------*/
	
	private final List<ParamProcessorFactory> factories;
	private final ParamProcessorFactory defaultFactory;

/*---------------------------- constructors ----------------------------*/
	
	public ParamProcessorsService() {
		factories = new LinkedList<ParamProcessorFactory>();
		
		registerParamProcessor(new HttpPostParam.Factory());
		registerParamProcessor(new UriParam.Factory());
		registerParamProcessor(new SessionAttributeParam.Factory());
		registerParamProcessor(new ModelParam.Factory());
		registerParamProcessor(new RequestParam.Factory());
		registerParamProcessor(new ResponseParam.Factory());
		
		defaultFactory = new DefaultParamFactory(); 
	}
	
/*------------------------------- methods ------------------------------*/
	
	public void registerParamProcessor(ParamProcessorFactory processorFactory) {
		factories.add(processorFactory);
	}
	
	public ParamProcessor createProcessor(ParamMetadata metadata) {
		for (ParamProcessorFactory factory : factories) {
			ParamProcessor processor = factory.buildParamProcessor(metadata);
			if (processor != null) {
				return processor;
			}
		}
		
		return defaultFactory.buildParamProcessor(metadata);
	}

/*----------------------------------------------------------------------*/
}
