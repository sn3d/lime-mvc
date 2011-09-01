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
package org.zdevra.guice.mvc.converters;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import org.zdevra.guice.mvc.exceptions.IllegalConversionException;
import org.zdevra.guice.mvc.ConversionService.Converter;
import org.zdevra.guice.mvc.ConversionService.ConverterFactory;
import org.zdevra.guice.mvc.FactoryMethod;
import org.zdevra.guice.mvc.Utils;

/**
 * Default convertor is used in case, when no convertor
 * is matched to parameter's type and in most cases throws 
 * {@link org.zdevra.guice.mvc.exceptions.IllegalConversionException}.
 * 
 */
public class DefaultConverter implements Converter {
	
/*---------------------------- m. variables ----------------------------*/
	
	private final Class<?> type;
	private final Method   factoryMethod;
	
/*----------------------------------------------------------------------*/
	
	/**
	 * Factory class for {@link DefaultConverter}
	 */
	public static class Factory implements ConverterFactory {		
		@Override
		public Converter createConvertor(Class<?> type, Annotation[] annotations) {
			
			//get a factory method if is presented in an object
			List<Method> factories = Utils.getAnnotatedMethods(type, FactoryMethod.class);
			for(Method factory : factories) {
				Class<?>[] params = factory.getParameterTypes();
				Class<?> returnType = factory.getReturnType();
				if (returnType != null) {
					if (params != null) {
						if (params.length == 1) {
							if (params[0] == String.class) {								
								return new DefaultConverter(type, factory);
							}
						}
					}
				}
			}
			
			return new DefaultConverter(type);
		}		
	}
		
	
	private DefaultConverter(Class<?> type) {
		this(type, null);
	}
	
	
	private DefaultConverter(Class<?> type, Method factoryMethod) {
		this.type = type;
		this.factoryMethod = factoryMethod;
	}
	
/*----------------------------------------------------------------------*/
	
	
	@Override
	public Object convert(String stringValue) {
		
		if (factoryMethod == null) {
			throw new IllegalConversionException("conversion from string '" + stringValue + "' to " + type.getName() + " failed. No factory method found.");
		}
		
		try {
			Object[] args = new Object[1];
			args[0] = stringValue;
			return factoryMethod.invoke(null, args);
		} catch (Exception e) {
			throw new IllegalConversionException("conversion from string '" + stringValue + "' to " + type.getName() + " failed. Can't invoke factory method");
		}
	}


	@Override
	public Object convert(String[] stringArrayValue) {
		throw new IllegalConversionException("conversion from string '" + stringArrayValue + "' to " + type.getName() + " array failed. Can't invoke factory method");
	}

/*----------------------------------------------------------------------*/
}
