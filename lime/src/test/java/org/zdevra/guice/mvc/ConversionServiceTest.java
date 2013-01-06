/**
 * ***************************************************************************
 * Copyright 2011 Zdenko Vrabel
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 ****************************************************************************
 */
package org.zdevra.guice.mvc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.zdevra.guice.mvc.ConversionService.Converter;
import org.zdevra.guice.mvc.ConversionService.ConverterFactory;
import org.zdevra.guice.mvc.annotations.BooleanConv;
import org.zdevra.guice.mvc.annotations.DateConv;
import org.zdevra.guice.mvc.annotations.FactoryMethod;
import org.zdevra.guice.mvc.converters.*;
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

        public void booleanTest(@BooleanConv(trueVal = "Y", falseVal = "N") boolean x) {
        }

        public void dateTest(@DateConv("yyyy") Date x) {
        }

        public void dateTest2(@DateConv(value = "yyyy", defaultValue = "2000") Date x) {
        }
    }
    /*---------------------------- m. variables ----------------------------*/
    private ConversionService conversion;

    /*------------------------------- methods ------------------------------*/
    @BeforeTest
    public void init() {
        Set<ConverterFactory> converters = new HashSet<ConverterFactory>();
        converters.add(new DateConverterFactory());
        converters.add(new BooleanConverterFactory());
        converters.add(new DoubleConverterFactory());
        converters.add(new FloatConverterFactory());
        converters.add(new IntegerConverterFactory());
        converters.add(new LongConverterFactory());
        converters.add(new StringConverterFactory());
        conversion = new ConversionService(converters);
    }

    @Test(enabled = false)
    public void testBoolean() throws SecurityException, NoSuchMethodException {
        Method m = MyController.class.getMethods()[0];
        Annotation[][] annotations = m.getParameterAnnotations();

        Converter<?> converter = conversion.getConverter(Boolean.class, annotations[0]);
        Map<String, String[]> data = new HashMap<String, String[]>();

        //test with defined annotation
        data.put("val1", new String[]{"Y"});
        Object val = converter.convert("val1", data);
        Assert.assertTrue(val instanceof Boolean);
        Assert.assertEquals(Boolean.TRUE, val);

        data.put("val2", new String[]{"N"});
        val = converter.convert("val2", data);
        Assert.assertTrue(val instanceof Boolean);
        Assert.assertEquals(Boolean.FALSE, val);

        //test without defined annotation
        converter = conversion.getConverter(Boolean.class, null);
        data.put("val3", new String[]{"true"});
        val = converter.convert("val3", data);
        Assert.assertTrue(val instanceof Boolean);
        Assert.assertTrue(Boolean.TRUE == val);

        data.put("val4", new String[]{"false"});
        val = converter.convert("val4", data);
        Assert.assertTrue(val instanceof Boolean);
        Assert.assertTrue(Boolean.FALSE == val);

        val = converter.convert("val1", data);
        Assert.assertTrue(val instanceof Boolean);
        Assert.assertTrue(Boolean.FALSE == val);

        //test as array
        converter = conversion.getConverter(Boolean[].class, null);
        data.put("val5", new String[]{"true", "false", "false", "true"});
        val = converter.convert("val5", data);
        Assert.assertTrue(val instanceof Boolean[]);
        Boolean[] array = (Boolean[]) val;
        Assert.assertTrue(array.length == 4);
        Assert.assertTrue(array[0] == Boolean.TRUE);
        Assert.assertTrue(array[1] == Boolean.FALSE);
        Assert.assertTrue(array[2] == Boolean.FALSE);
        Assert.assertTrue(array[3] == Boolean.TRUE);
    }

    @Test
    public void testObject() {
        try {
            Converter<?> converter = conversion.getConverter(SomeObject.class, null);
            Assert.assertFalse("For unknown object the NoConverter exception have to be caught", true);
        } catch (NoConverterException e) {
            //OK
        } catch (Exception e) {
            Assert.assertFalse("For unknown object the NoConverter exception have to be caught", true);
        }
    }

    @Test
    public void testString() {
        Converter<?> converter = conversion.getConverter(String.class, null);
        Map<String, String[]> data = new HashMap<String, String[]>();

        data.put("val1", new String[]{"aaa"});
        data.put("val2", new String[]{"bbb", "ccc"});

        //single conversion
        Object val = converter.convert("val1", data);
        Assert.assertTrue(val instanceof String);
        Assert.assertTrue("aaa".equals(val));

        //convert a array of strings
        converter = conversion.getConverter(String[].class, null);
        val = converter.convert("val2", data);
        Assert.assertTrue(val instanceof String[]);
        String[] arr = (String[]) val;
        Assert.assertTrue("bbb".equals(arr[0]));
        Assert.assertTrue("ccc".equals(arr[1]));


    }

    @Test(enabled = false)
    public void testDate() {
        DateFormat df = new SimpleDateFormat("yyyy");
        Method m = MyController.class.getMethods()[1];
        Annotation[][] annotations = m.getParameterAnnotations();
        Map<String, String[]> data = new HashMap<String, String[]>();

        //test of conversion
        data.put("val1", new String[]{"2011"});
        Converter<?> converter = conversion.getConverter(Date.class, annotations[0]);
        Object val = converter.convert("val1", data);

        Assert.assertTrue(val instanceof Date);
        Assert.assertTrue("2011".equals(df.format(val)));

        //test of scenario 1
        try {
            data.put("val2", new String[]{"a"});
            val = converter.convert("val2", data);
            Assert.fail();
        } catch (Exception e) {
            //ok
        }

        //test of scenario 2
        m = MyController.class.getMethods()[2];
        annotations = m.getParameterAnnotations();
        converter = conversion.getConverter(Date.class, annotations[0]);
        data.put("val3", new String[]{"a"});

        val = converter.convert("val3", data);
        Assert.assertTrue(val instanceof Date);
        Assert.assertTrue("2000".equals(df.format(val)));
    }

    @Test
    public void testDouble() {
        Converter<?> converter = conversion.getConverter(Double.class, null);

        Map<String, String[]> data = new HashMap<String, String[]>();
        data.put("val1", new String[]{"1.1"});
        data.put("val2", new String[]{"2.2", "3.3", "4.4"});

        //convert a single value
        Object val = converter.convert("val1", data);
        Assert.assertTrue(val instanceof Double);
        Assert.assertTrue(new Double(1.1).equals(val));

        //convert a array
        converter = conversion.getConverter(Double[].class, null);
        val = converter.convert("val2", data);
        Assert.assertTrue(val instanceof Double[]);
        Double[] arr = (Double[]) val;
        Assert.assertTrue(new Double(2.2).equals(arr[0]));
        Assert.assertTrue(new Double(3.3).equals(arr[1]));
        Assert.assertTrue(new Double(4.4).equals(arr[2]));
    }

    @Test
    public void testLong() {
        Converter<?> converter = conversion.getConverter(Long.class, null);

        Map<String, String[]> data = new HashMap<String, String[]>();
        data.put("val1", new String[]{"1"});
        data.put("val2", new String[]{"2", "3", "4"});

        //convert a single value
        Object val = converter.convert("val1", data);
        Assert.assertTrue(val instanceof Long);
        Assert.assertTrue(new Long(1).equals(val));

        //convert a array
        converter = conversion.getConverter(Long[].class, null);
        val = converter.convert("val2", data);
        Assert.assertTrue(val instanceof Long[]);
        Long[] arr = (Long[]) val;
        Assert.assertTrue(new Long(2).equals(arr[0]));
        Assert.assertTrue(new Long(3).equals(arr[1]));
        Assert.assertTrue(new Long(4).equals(arr[2]));


        //convert a array
        converter = conversion.getConverter(long[].class, null);
        val = converter.convert("val2", data);
        Assert.assertTrue(val instanceof long[]);
        long[] arr2 = (long[]) val;
        Assert.assertTrue(new Long(2).equals(arr2[0]));
        Assert.assertTrue(new Long(3).equals(arr2[1]));
        Assert.assertTrue(new Long(4).equals(arr2[2]));

    }

    @Test
    public void testFloat() {
        Converter<?> converter = conversion.getConverter(Float.class, null);

        Map<String, String[]> data = new HashMap<String, String[]>();
        data.put("val1", new String[]{"1.1f"});
        data.put("val2", new String[]{"2.2f", "3.3f", "4.4f"});

        //convert a single value
        Object val = converter.convert("val1", data);
        Assert.assertTrue(val instanceof Float);
        Assert.assertTrue(new Float(1.1f).equals(val));

        //convert a array
        converter = conversion.getConverter(Float[].class, null);
        val = converter.convert("val2", data);
        Assert.assertTrue(val instanceof Float[]);
        Float[] arr = (Float[]) val;
        Assert.assertTrue(new Float(2.2f).equals(arr[0]));
        Assert.assertTrue(new Float(3.3f).equals(arr[1]));
        Assert.assertTrue(new Float(4.4f).equals(arr[2]));
    }

    @Test
    public void testInteger() {
        Converter<?> converter = conversion.getConverter(Integer.class, null);

        Map<String, String[]> data = new HashMap<String, String[]>();
        data.put("val1", new String[]{"1"});
        data.put("val2", new String[]{"2", "3", "4"});

        //convert a single value
        Object val = converter.convert("val1", data);
        Assert.assertTrue(val instanceof Integer);
        Assert.assertTrue(new Integer(1).equals(val));

        //convert a array
        converter = conversion.getConverter(Integer[].class, null);
        val = converter.convert("val2", data);
        Assert.assertTrue(val instanceof Integer[]);
        Integer[] arr = (Integer[]) val;
        Assert.assertTrue(new Integer(2).equals(arr[0]));
        Assert.assertTrue(new Integer(3).equals(arr[1]));
        Assert.assertTrue(new Integer(4).equals(arr[2]));
    }
    /*----------------------------------------------------------------------*/
}
