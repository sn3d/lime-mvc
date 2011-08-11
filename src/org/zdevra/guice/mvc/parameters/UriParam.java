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

import java.lang.annotation.Annotation;
import org.zdevra.guice.mvc.ConversionService;
import org.zdevra.guice.mvc.ConversionService.Convertor;
import org.zdevra.guice.mvc.InvokeData;
import org.zdevra.guice.mvc.UriParameter;
import org.zdevra.guice.mvc.Utils;

/**
 * The parameter's parameter processor for {@link org.zdevra.guice.mvc.UriParameter} annotation.
 * 
 * The Url in {@literal @}RequestMapping of the method can be a regular expression. 
 * {@link org.zdevra.guice.mvc.UriParameter} pick up the value of some regexp. group and put it into method's parameter.
 * <p>
 * 
 * Processor uses the same type conversion like RequestParam processor.
 * 
 */
public final class UriParam implements ParamProcessor {

/*---------------------------- m. variables ----------------------------*/

	private final int group;
	private final Convertor convertor;
	
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
			
			Convertor typeConvertor = convrtService.getConvertor(paramType, paramAnnotations);						
			return new UriParam(annotation.value(), typeConvertor);
		}		
	}

/*----------------------------------------------------------------------*/

	/**
	 * Hidden constructor. For the processor's constuction is used Factory 
	 * class.  
	 */
	private UriParam(int group, Convertor convertor) {
		super();
		this.group = group;
		this.convertor = convertor;
	}		

	
	public Object getValue(InvokeData data) {
		String stringValue = data.getUriMatcher().group(group);
		Object convertedValue = convertor.convert(stringValue);
		return convertedValue;
	}

/*----------------------------------------------------------------------*/
}
