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
package org.zdevra.guice.mvc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * The class contains static utility methods.
 */
public final class Utils {

/*----------------------------------------------------------------------*/

    /**
     * Hidden constructor
     */
    private Utils() {
    }
    

	/**
	 * Method searchs annotation in annotations array and return
	 * it. If annotation is not occured, then method return null.
	 *  
	 * @param lookingForAnnotation
	 * @param annotations
	 * @return 
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final <T extends Annotation> T getAnnotation(Class<T> lookingFor, Annotation[] annotations) 
	{
		if (annotations != null) {
			for (int i = 0; i < annotations.length; ++i) {
				if (lookingFor.isInstance(annotations[i])) {
					return (T) annotations[i];
				}
			}
		}
		return null;
	}
	
	
	/**
	 * The method return all methods in the class annotated by 
	 * concrete annotation.
	 * @param annotation
	 * @return
	 */
	public static final List<Method> getAnnotatedMethods(Class<?> type, Class<? extends Annotation> annotation) 
	{
		List<Method> res = new LinkedList<Method>();
		Method[] methods = type.getMethods();
		for (int i = 0; i < methods.length; ++i) {
			Annotation a = methods[i].getAnnotation(annotation);
			if (a != null) {
				res.add(methods[i]);
			}
		}				
		return res;
	}
	
/*----------------------------------------------------------------------*/
}
