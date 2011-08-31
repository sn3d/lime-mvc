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
import java.util.Collection;
import java.util.Set;

import org.zdevra.guice.mvc.convertors.DefaultConvertor;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The class provides conversions from incomming strings from HTTP requests 
 * to a various datatypes. If there is no convertor for that datatype, 
 * then is used {@link DefaultConvertor}. 
 * 
 * The class is used internally by {@link MethodInvokerImpl}.
 * 
 * You may implement and register your own convertor. Just
 * implements the {@link ConvertorService.Convertor}, {@link ConvertorService.ConvertorFactory} 
 * interfaces and in your configureControllers() register the convertor:
 * 
 * <pre class="prettyprint">
 * class MyModule extends MvcModule {
 * 
 *    protected void configureControllers() {
 *       ...
 *       registerConvertor(MyType.class, new MyConvertorFactory());
 *       ...
 *    }
 * }
 * </pre>
 * 
 * All supported convertors in Lime are placed in package {@link org.zdevra.guice.mvc.convertors}.
 * 
 * @see ConversionService.Convertor
 * @see ConversionService.ConvertorFactory
 */
@Singleton
public class ConversionService {
/*---------------------------- m. variables ----------------------------*/

	private final Collection<ConvertorFactory> factories;
	private ConvertorFactory defaultConvertorFactory;
	
/*----------------------------------------------------------------------*/
	
	/**
	 * Interface for convertor
	 */
	public interface Convertor {		
		public Object convert(String stringValue);
		public Object convert(String[] stringArray);
	}
	
	
	/**
	 * Interface for contertor factory which is constructing convertor impl.
	 *
	 */
	public interface ConvertorFactory {
		public Convertor createConvertor(Class<?> type, Annotation[] annotations); 
	}
	
/*---------------------------- constructors ----------------------------*/
	
	/**
	 * Constructor
	 */
	@Inject
	public ConversionService(Set<ConvertorFactory> factories) {
		this.defaultConvertorFactory = new DefaultConvertor.Factory();
		this.factories = factories;
	}
	
/*------------------------------- methods ------------------------------*/
			
	
	/**
	 * Method convert string value to object.
	 */
	public Object convert(Class<?> type, Annotation[] annotations, String stringValue) 
	{	
		Convertor convertor = getConvertor(type, annotations);
		Object convertedVal = convertor.convert(stringValue);		
		return convertedVal;
	}
	
	
	/**
	 * Method convert string array to object.
	 */
	public Object convert(Class<?> type, Annotation[] annotations, String[] stringValue) 
	{	
		if (!type.isArray()) {
			return null;
		}

        Class<?> componentType = type.getComponentType();
		Convertor convertor = getConvertor(componentType, annotations);
		Object convertedVal = convertor.convert(stringValue);
		
		return convertedVal;		
	}
	
	
	/**
	 * Method return right convertor for type
	 * 
	 * @param type
	 * @return
	 */
	public Convertor getConvertor(Class<?> type, Annotation[] annotations) 
	{
		for (ConvertorFactory factory : factories) {
			Convertor convertor = factory.createConvertor(type, annotations);
			if (convertor != null) {
				return convertor;
			}
		}
		
		return defaultConvertorFactory.createConvertor(type, annotations);
	}


/*----------------------------------------------------------------------*/
}
