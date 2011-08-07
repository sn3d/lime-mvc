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

import javax.servlet.http.HttpServletRequest;

import org.zdevra.guice.mvc.InvokeData;

/**
 * The parameter's  processor is executed when the method has a parameter, 
 * which is a instance of HttpServletRequest, and put the request object
 * into this parameter.
 * <p>
 * 
 * for example:
 * <pre class="prettyprint">
 * {@literal @}RequestMapping("/control");
 * public void controllerMethod(HttpServletRequest request) {
 *    request.getParameter("param");
 * }
 * </pre>
 */
public class RequestParam implements ParamProcessor {
/*----------------------------------------------------------------------*/
	
	/**
	 * Factory sub-class constructs a request parameter processor only once
	 * because the processor is stateless.
	 */
	static class Factory implements ParamProcessorFactory 
	{
		private final ParamProcessor processor;
		
		public Factory() {
			processor = new RequestParam();
		}

		@Override
		public ParamProcessor buildParamProcessor(ParamMetadata metadata) {
			if (metadata.getType() != HttpServletRequest.class) {
				return null;	
			}
			return processor;
		}		
	}

/*------------------------------- methods ------------------------------*/

	/**
	 * Hidden constructor
	 */
	private RequestParam() {
	}

	@Override
	public Object getValue(InvokeData data) {
		return data.getRequest();
	}

/*----------------------------------------------------------------------*/
}
