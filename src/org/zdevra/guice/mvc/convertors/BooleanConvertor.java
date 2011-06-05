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
package org.zdevra.guice.mvc.convertors;

import java.lang.annotation.Annotation;

import org.zdevra.guice.mvc.ConversionService.Convertor;
import org.zdevra.guice.mvc.ConversionService.ConvertorFactory;
import org.zdevra.guice.mvc.Utils;

/**
 * This impl. of convertor is responsible for converting string
 * to boolean. 
 * 
 * The parameter may be annotated by BooleanParam annotation which
 * carying metadata information about string form for true and false.
 * 
 * Let's assume that we don't want convert regular 'true' 'false' strings, but
 * we want convert Y like true a N like false:
 * 
 * <pre><code>
 * @Controller
 * public class MyController {
 * 
 *    @RequestMapping(path="/method")
 *    public void doSomething( @RequestParameter("bool-param") @BooleanConv(trueVal="Y", falseVal="N") boolean param) 
 *    {
 *    	
 *    }
 * }
 * </pre><code>
 * 
 *
 */
public final class BooleanConvertor extends ArrayConvertor<Boolean> implements Convertor {
	
/*---------------------------- m. variables ----------------------------*/
	
	private final BooleanConv boolAnnotation;
	private final Class<?> type;
	
/*---------------------------- constructors ----------------------------*/
	
	public static class Factory implements ConvertorFactory {

		public Convertor createConvertor(Class<?> type, Annotation[] annotations) {
			BooleanConv ba = Utils.getAnnotation(BooleanConv.class, annotations); 
			return new BooleanConvertor(ba, type);
		}		
	}
		
	private BooleanConvertor(BooleanConv boolAnnotation, Class<?> type) {
		this.boolAnnotation = boolAnnotation;
		this.type = type;
	}	
		
/*------------------------------- methods ------------------------------*/
	
	public Object convert(String stringValue)
	{
		if (boolAnnotation != null) {
			
			if ( boolAnnotation.trueVal().equalsIgnoreCase(stringValue) ) {
				return true;
			} else if ( boolAnnotation.falseVal().equalsIgnoreCase(stringValue) ) {
				return false;
			} else {
				return false;
			}
		} 
		return Boolean.parseBoolean(stringValue);
	}

    
	public Object convert(String[] stringArray) {
		return convertArray(stringArray, this, type);
	}
		
}
