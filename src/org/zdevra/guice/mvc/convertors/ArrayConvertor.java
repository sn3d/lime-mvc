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

import java.lang.reflect.Array;

import org.zdevra.guice.mvc.ConversionService.Convertor;

public class ArrayConvertor<T> {
	
	protected Object convertArray(String[] stringArray, Convertor conv, Class<?> clazz) {
		Object x = Array.newInstance(clazz, stringArray.length);		
		for (int i = 0; i < stringArray.length; ++i) {
			Object val = conv.convert(stringArray[i]);
			Array.set(x, i, val);
		}
		return x;
	}

}
