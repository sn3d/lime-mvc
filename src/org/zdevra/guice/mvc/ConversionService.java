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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.zdevra.guice.mvc.convertors.BooleanConvertor;
import org.zdevra.guice.mvc.convertors.DateConvertor;
import org.zdevra.guice.mvc.convertors.DefaultConvertor;
import org.zdevra.guice.mvc.convertors.DoubleConvertor;
import org.zdevra.guice.mvc.convertors.FloatConvertor;
import org.zdevra.guice.mvc.convertors.IntegerConvertor;
import org.zdevra.guice.mvc.convertors.LongConvertor;
import org.zdevra.guice.mvc.convertors.StringConvertor;

/**
 * The class is responsible for converting strings to a various 
 * datatypes. 
 * 
 * It's a composition of convertors with ability to registering a custom
 * convertors. Your own convertor must have to implement the 
 * ConversionService.Convertor interface.
 * 
 * If there is no convertor for that datatype, then is used DefaultConvertor.
 */
public class ConversionService {
/*---------------------------- m. variables ----------------------------*/

	private Map<Class<?>, ConvertorFactory> regiConvertorFactories;
	private ConvertorFactory defaultConvertor;
	
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
	public ConversionService() {
		this.regiConvertorFactories = new HashMap<Class<?>, ConversionService.ConvertorFactory>();
		this.defaultConvertor = new DefaultConvertor.Factory();
		
		//registration of the basic convertors
		registerConvertor(boolean.class, new BooleanConvertor.Factory());
		registerConvertor(Boolean.class, new BooleanConvertor.Factory());		
		registerConvertor(int.class, new IntegerConvertor.Factory());
		registerConvertor(Integer.class, new IntegerConvertor.Factory());
		registerConvertor(Long.class, new LongConvertor.Factory());
		registerConvertor(long.class, new LongConvertor.Factory());
		registerConvertor(Float.class, new FloatConvertor.Factory());
		registerConvertor(float.class, new FloatConvertor.Factory());
		registerConvertor(Double.class, new DoubleConvertor.Factory());
		registerConvertor(double.class, new DoubleConvertor.Factory());
		registerConvertor(String.class, new StringConvertor.Factory());
		registerConvertor(Date.class, new DateConvertor.Factory());
	}
	
/*------------------------------- methods ------------------------------*/
		
	/**
	 * Method register a convertor
	 */
	public void registerConvertor(Class<?> type, ConvertorFactory factoryToRegister) {
		regiConvertorFactories.put(type, factoryToRegister);
	}
	
	
	/**
	 * Method return right convertor for type
	 * 
	 * @param type
	 * @return
	 */
	public Convertor getConvertor(Class<?> type, Annotation[] annotations) 
	{
		//get convertor
		ConvertorFactory factory = regiConvertorFactories.get(type);
		if (factory == null) {
			factory = defaultConvertor;
		}
		
		Convertor convertor = factory.createConvertor(type, annotations);
		return convertor;
	}
	
	
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


/*----------------------------------------------------------------------*/
}
