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
import java.util.Map;
import java.util.Set;

import org.zdevra.guice.mvc.converters.NoConverterFactory;
import org.zdevra.guice.mvc.exceptions.NoConverterException;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The class provides conversions from incomming strings from HTTP requests 
 * to a various datatypes.
 * 
 * The class is used internally by {@link MethodInvokerImpl}.
 * 
 * You may implement and register your own converter. Just
 * implements the {@link ConversionService.Converter}, {@link ConversionService.ConverterFactory} 
 * interfaces and in your configureControllers() register the converter:
 * 
 * <pre class="prettyprint">
 * class MyModule extends MvcModule {
 * 
 *    protected void configureControllers() {
 *       ...
 *       registerConverter(MyConverterFactory.class);
 *       ...
 *    }
 * }
 * </pre>
 * 
 * All supported converters in Lime are placed in package {@link org.zdevra.guice.mvc.converters}.
 * 
 * @see ConversionService.Converter
 * @see ConversionService.ConverterFactory
 */
@Singleton
public class ConversionService {
/*---------------------------- m. variables ----------------------------*/

	private final Collection<ConverterFactory> factories;
	
/*----------------------------------------------------------------------*/
	
	/**
	 * Interface for converter
	 */
	public interface Converter<T> {		
		public T convert(String name, Map<String, String[]> data);
	}
	
	
	/**
	 * Interface for contertor factory which is constructing converter impl.
	 *
	 */
	public interface ConverterFactory {
		public Converter<?> createConvertor(Class<?> type, Annotation[] annotations); 
	}
	
/*---------------------------- constructors ----------------------------*/
	
	/**
	 * Constructor
	 */
	@Inject
	public ConversionService(Set<ConverterFactory> factories) {
		this.factories = factories;
	}
	
/*------------------------------- methods ------------------------------*/

	/**
	 * Method return right converter for type
	 * 
	 * @param type
	 * @return
	 */
	public Converter<?> getConverter(Class<?> type, Annotation[] annotations) 
	{
		for (ConverterFactory factory : factories) {
			Converter<?> converter = factory.createConvertor(type, annotations);
			if (converter != null) {
				return converter;
			}
		}
		
		throw new NoConverterException(type);
	}


    /**
     * Method return converter from given factory class. If factory class is {@link NoConverterFactory}, then
     * is called origin method of getConverter()
     */
    public Converter<?> getConverter(Class<? extends ConverterFactory> converterFactoryClass, Class<?> type, Annotation[] annotations)
    {
        if (converterFactoryClass == NoConverterFactory.class) {
            return  getConverter(type, annotations);
        }

        for (ConverterFactory factory : factories) {
            if (factory.getClass() == converterFactoryClass) {
                Converter<?> converter = factory.createConvertor(type, annotations);
                if (converter != null) {
                    return converter;
                }
            }
        }
        throw new NoConverterException(converterFactoryClass, type);
    }


/*----------------------------------------------------------------------*/
}
