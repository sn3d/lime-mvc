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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.zdevra.guice.mvc.Utils;
import org.zdevra.guice.mvc.ConversionService.Convertor;
import org.zdevra.guice.mvc.ConversionService.ConvertorFactory;
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
public class DateConvertor extends ArrayConvertor<Date> implements Convertor {
	
/*---------------------------- m. variables ----------------------------*/
		
	private final DateFormat dateFormat;
	private final Date defaultDate;

/*---------------------------- constructors ----------------------------*/
	
	
	/**
	 * Factory for {@link DateConvertor}
	 */
	public static class Factory implements ConvertorFactory {
		@Override
		public Convertor createConvertor(Class<?> type, Annotation[] annotations) {
			DateConv da = Utils.getAnnotation(DateConv.class, annotations);
			DateFormat df = new SimpleDateFormat(da.value());
			
			try {
				return new DateConvertor(df, df.parse(da.defaultValue()));
			} catch (ParseException e) {
				return new DateConvertor(df);
			}				
		}				
	}

	
	private DateConvertor(DateFormat dateFormater) {
		this(dateFormater, null);
	}

	
	private DateConvertor(DateFormat dateFormater, Date defaultDate) {
		this.dateFormat = dateFormater;
		this.defaultDate = defaultDate; 
	}
	
/*------------------------------- methods ------------------------------*/
	
	@Override
	public Object convert(String stringValue) {		
		try {
			return this.dateFormat.parse(stringValue);
		} catch (Exception e) {
			if (this.defaultDate != null) {
				return defaultDate;
			}
			throw new IllegalConversionException("A conversion from the '" + stringValue + "' to the date '" + this.dateFormat.toString() + "' failed");
		}		
	}


	@Override
	public Object convert(String[] stringArray) {
		return convertArray(stringArray, this, Date.class);
	}
	
/*----------------------------------------------------------------------*/
	
}
