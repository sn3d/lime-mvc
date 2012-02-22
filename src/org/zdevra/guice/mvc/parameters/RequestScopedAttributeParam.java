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

import org.zdevra.guice.mvc.InvokeData;
import org.zdevra.guice.mvc.Utils;
import org.zdevra.guice.mvc.annotations.RequestScopedAttribute;

/**
 * Processor for {@link org.zdevra.guice.mvc.annotations.RequestScopedAttribute} annotation. This class
 * gets the value and fill-in the method's parameter.
 *
 * @see org.zdevra.guice.mvc.annotations.RequestScopedAttribute
 */
public class RequestScopedAttributeParam implements ParamProcessor {

	//-----------------------------------------------------------------------------------------------------------
	// m. variables
	//-----------------------------------------------------------------------------------------------------------
	private final String attributeName;
	private final Class<?> methodParamType;

	
	//-----------------------------------------------------------------------------------------------------------
	// constructor & factory class
	//-----------------------------------------------------------------------------------------------------------
	
	/**
	 * Class construct and initialize parameter processor
	 */
	public static class Factory implements ParamProcessorFactory {

		@Override
		public ParamProcessor buildParamProcessor(ParamMetadata metadata) {
			RequestScopedAttribute annotation = Utils.getAnnotation(RequestScopedAttribute.class, metadata.getAnnotations());
			if (annotation == null) {
				return null;
			}						
			return new RequestScopedAttributeParam(annotation.value(), metadata.getType());

		}		
	}

	/**
	 * Constructor
	 * @param attributeName
	 */
	private RequestScopedAttributeParam(String attributeName, Class<?> methodParamType) 
	{
		super();
		this.attributeName = attributeName;
		this.methodParamType = methodParamType;
	}

	
	//-----------------------------------------------------------------------------------------------------------
	// methods
	//-----------------------------------------------------------------------------------------------------------

	@Override
	public Object getValue(InvokeData data) 
	{
		Object value = data.getRequest().getAttribute(attributeName);
		if (value != null) {
			if (methodParamType.isInstance(value)) {
				return value;
			} else {
				throw new IllegalStateException("the object in the request is not " + methodParamType.getName() + " but is " + value.getClass().getName());
			}
		}
		return null;
	}

}
