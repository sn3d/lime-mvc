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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.zdevra.guice.mvc.ConversionService.ConvertorFactory;
import org.zdevra.guice.mvc.convertors.BooleanConv;
import org.zdevra.guice.mvc.convertors.BooleanConvertor;
import org.zdevra.guice.mvc.convertors.DateConv;
import org.zdevra.guice.mvc.convertors.DateConvertor;
import org.zdevra.guice.mvc.convertors.DoubleConvertor;
import org.zdevra.guice.mvc.convertors.FloatConvertor;
import org.zdevra.guice.mvc.convertors.IntegerConvertor;
import org.zdevra.guice.mvc.convertors.LongConvertor;
import org.zdevra.guice.mvc.convertors.StringConvertor;
import org.zdevra.guice.mvc.exceptions.NoConverterException;

@Test
public class ConversionServiceTest {
	
/*----------------------------------------------------------------------*/
	
	public static class SomeObject {
		
		public String val;
		
		@FactoryMethod
		public static SomeObject createObj(String val) {
			SomeObject obj = new SomeObject();
			obj.val = val;
			return obj;
		}
		
	}
	
	public static class MyController {		
		
		public void booleanTest( @BooleanConv(trueVal="Y", falseVal="N") boolean x) {		
		}		
		
		public void dateTest( @DateConv("yyyy") Date x ) {
		}
		
		public void dateTest2( @DateConv(value="yyyy", defaultValue="2000") Date x ) {			
		}
		
	}	
	
/*---------------------------- m. variables ----------------------------*/	
	
	private ConversionService conversion;
	
/*------------------------------- methods ------------------------------*/
	
	@BeforeTest
	public void init() {	
		Set<ConvertorFactory> converters = new HashSet<ConvertorFactory>();
		converters.add(new DateConvertor.Factory());
		converters.add(new BooleanConvertor.Factory());
		converters.add(new DoubleConvertor.Factory());
		converters.add(new LongConvertor.Factory());
		converters.add(new FloatConvertor.Factory());
		converters.add(new IntegerConvertor.Factory());
		converters.add(new StringConvertor.Factory());
		conversion = new ConversionService(converters);		
	}
	
	
	@Test
	public void testBoolean() throws SecurityException, NoSuchMethodException {		
		Method m = MyController.class.getMethods()[0];
		Annotation[][] annotations = m.getParameterAnnotations();
		
		Object val = conversion.convert(Boolean.class, annotations[0], "Y");
		Assert.assertTrue(val instanceof Boolean);
		Assert.assertTrue(Boolean.TRUE == val);
		
		val = conversion.convert(Boolean.class, annotations[0], "N");
		Assert.assertTrue(val instanceof Boolean);
		Assert.assertTrue(Boolean.FALSE == val);
		
		val = conversion.convert(Boolean.class, null, "true");
		Assert.assertTrue(val instanceof Boolean);
		Assert.assertTrue(Boolean.TRUE == val);
		
		val = conversion.convert(Boolean.class, null, "tr");
		Assert.assertTrue(val instanceof Boolean);
		Assert.assertTrue(Boolean.FALSE == val);
		
		val = conversion.convert(Boolean.class, null, "false");
		Assert.assertTrue(val instanceof Boolean);
		Assert.assertTrue(Boolean.FALSE == val);
	}
	
	
	@Test
	public void testObject() {
		try {
			Object val = conversion.convert(SomeObject.class, null, "value");
			Assert.assertFalse("For unknown object the NoConverter exception have to be caught", true);
		} catch (NoConverterException e) {
			//OK
		} catch (Exception e) {
			Assert.assertFalse("For unknown object the NoConverter exception have to be caught", true);
		}
	}
	
	
	@Test
	public void testString() {
		Object val = conversion.convert(String.class, null, "value");
		Assert.assertTrue(val instanceof String);
		Assert.assertTrue("value".equals(val));
	}
	
	
	@Test
	public void testDate() {
		DateFormat df = new SimpleDateFormat("yyyy");
		Method m = MyController.class.getMethods()[1];
		Annotation[][] annotations = m.getParameterAnnotations();

		//test of conversion
		Object val = conversion.convert(Date.class, annotations[0], "2011");
		Assert.assertTrue(val instanceof Date);
		Assert.assertTrue("2011".equals(df.format(val)));
		
		//test of scenario 1
		try {
			val = conversion.convert(Date.class, annotations[0], "a");
			Assert.fail();
		} catch (Exception e) {
			//ok
		}
		
		//test of scenario 2
		m = MyController.class.getMethods()[2];
		annotations = m.getParameterAnnotations();
		val = conversion.convert(Date.class, annotations[0], "a");
		Assert.assertTrue(val instanceof Date);
		Assert.assertTrue("2000".equals(df.format(val)));
	}
	
	
	@Test
	public void testInteger() {
		Integer v = new Integer(1);
		Object val = conversion.convert(Integer.class, null, v.toString());
		Assert.assertTrue(val instanceof Integer);
		Assert.assertTrue(v.equals(val));
	}


	@Test
	public void testDouble() {
		Double v = new Double(1);
		Object val = conversion.convert(Double.class, null, v.toString());
		Assert.assertTrue(val instanceof Double);
		Assert.assertTrue(v.equals(val));
	}
	
	
	@Test
	public void testLong() {
		Long v = new Long(1);
		Object val = conversion.convert(Long.class, null, v.toString());
		Assert.assertTrue(val instanceof Long);
		Assert.assertTrue(v.equals(val));
	}

	
	@Test
	public void testFloat() {
		Float v = new Float(1.2f);
		Object val = conversion.convert(Float.class, null, v.toString());
		Assert.assertTrue(val instanceof Float);
		Assert.assertTrue(v.equals(val));
	}
	
	@Test
	public void testIntArray() {
		String[] a = new String[] {"1", "2", "3"};		
		Object o = conversion.convert(int[].class, null, a);
		Assert.assertNotNull(o);
	}

/*----------------------------------------------------------------------*/
}
