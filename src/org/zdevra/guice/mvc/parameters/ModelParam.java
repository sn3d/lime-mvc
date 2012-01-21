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

import org.zdevra.guice.mvc.InvokeData;
import org.zdevra.guice.mvc.ModelMap;

/**
 * The parameter's processor take care of the {@link org.zdevra.guice.mvc.ModelMap} parameter in the method.
 * 
 * for example:
 * <pre class="prettyprint">
 * {@literal @}Path("/control")
 * public void controllerMethod(Model model) {
 *   ...
 * }
 * </pre>
 */
public class ModelParam implements ParamProcessor {
/*---------------------------- constructors ----------------------------*/
	
	/**
	 * Factory class for {@link ModelParam}
	 */
	public  static class Factory implements ParamProcessorFactory {
		
		private final ParamProcessor processor;
		
		public Factory() {
			processor = new ModelParam();
		}

		@Override
		public ParamProcessor buildParamProcessor(ParamMetadata metadata) {
			if (metadata.getType() != ModelMap.class) {
				return null;
			}
			return processor;
		}		
	}

	/**
	 * Hidden constructor. Use Factory class.
	 */
	private ModelParam() {		
	}
	
/*----------------------------------------------------------------------*/

	@Override
	public Object getValue(InvokeData data) {
		return data.getModel();
	}

/*----------------------------------------------------------------------*/
}
