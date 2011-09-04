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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.zdevra.guice.mvc.ConversionService.Converter;
import org.zdevra.guice.mvc.ConversionService.ConverterFactory;
import org.zdevra.guice.mvc.Utils;
import org.zdevra.guice.mvc.exceptions.IllegalConversionException;

/**
 * The class converts a string value to the date. Argument must be 
 * annotated by DateConv. This annotation declare format of date
 * which is used.
 * <p>
 * 
 * If the conversion failed, than there are 2 scenarios which can happend. The first 
 * scenario, when default date is not speciffied, then is throwed exception. The
 * second scenario, when default date is speciffied, then is used this default value
 * <p>
 * 
 * example of the scenario 1:
 * <pre class="prettyprint">
 * {@literal @}Controller
 * public class MyController {
 * 
 *    {@literal @}Path(path="/method")
 *    public void doSomething( {@literal @}RequestParameter("date-param") {@literal @}DateConv("YYYYMMDD") Date param) 
 *    {
 *    	...
 *    }
 * }
 * </pre>
 * 
 * <p>example of the scenario 2:
 * <pre class="prettyprint">
 * {@literal @}Controller
 * public class MyController {
 * 
 *    {@literal @}RequestMapping(path="/method")
 *    public void doSomething( {@literal @}RequestParameter("date-param") {@literal @}DateConv("YYYYMMDD", defaultValue="19900101") Date param) 
 *    {
 *    	...
 *    }
 * }
 * </pre>
 *
 */
public class DateConverter extends TypeConverter<Date> {
	
/*---------------------------- m. variables ----------------------------*/
		
	private final DateFormat dateFormat;
	private final Date defaultDate;

/*---------------------------- constructors ----------------------------*/
	
	
	/**
	 * Factory for {@link DateConverter}
	 */
	public static class Factory implements ConverterFactory {
		@Override
		public Converter<?> createConvertor(Class<?> type, Annotation[] annotations) {
			if (type != Date.class) {
				return null;
			}
			
			DateConv da = Utils.getAnnotation(DateConv.class, annotations);
			DateFormat df = new SimpleDateFormat(da.value());
			
			try {
				return new DateConverter(df, df.parse(da.defaultValue()));
			} catch (ParseException e) {
				return new DateConverter(df);
			}				
		}				
	}

	
	private DateConverter(DateFormat dateFormater) {
		this(dateFormater, null);
	}

	
	private DateConverter(DateFormat dateFormater, Date defaultDate) {
		this.dateFormat = dateFormater;
		this.defaultDate = defaultDate; 
	}

	
/*------------------------------- methods ------------------------------*/
	
	@Override
	protected Date convertType(String stringValue) {
		try {
			return this.dateFormat.parse(stringValue);
		} catch (Exception e) {
			if (this.defaultDate != null) {
				return defaultDate;
			}
			throw new IllegalConversionException("A conversion from the '" + stringValue + "' to the date '" + this.dateFormat.toString() + "' failed");
		}		
	}
		
/*----------------------------------------------------------------------*/
	
}
