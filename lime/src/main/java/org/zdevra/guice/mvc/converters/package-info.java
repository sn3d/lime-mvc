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
/**
 * This package contains all classes and related annotations, which provide conversions 
 * from strings to concrete data types.
 * 
 * Numbers are converted automatically. Special cases are Boolean and Date types. For
 * parameters of these types are used special annotations: 
 * 
 * <dl>
 * <dt>{@link org.zdevra.guice.mvc.converters.BooleanConv}
 * <dd>The annotation define how the string will convert to boolean.
 * 
 * <dt>{@link org.zdevra.guice.mvc.converters.DateConv}
 * <dd>The annotation define how the string will convert to Date.
 * </dl>
 * 
 * 
 * <br>
 * <h1>Custom converters</h1>
 *   
 * Normally, the controler's method attributes can be filled in from the HTTP request and they are 
 * automatically converted to few basic types like a String, Integer, Double, Date etc. Sometime we are 
 * in situations, when we want to have some custom type as a method's argument. Let's assume the 
 * controller's method addPerson:
 * 
 * <p><pre class="prettyprint">
 * {@literal @}Path("/person/add")
 * public void savePerson( {@literal @}RequestParameter("prs") Person personToSave) {
 *    ...
 * }
 * </pre>
 * 
 * Also assume HTML form which invokes the controller's method:
 * <p><pre class="prettyprint">
 * &lt;FORM action="person/add" method="post"&gt;
 *     Name: &lt;INPUT type="text" name="prs-name" /&gt;
 *     Surname: &lt;INPUT type="text" name="prs-surname" /&gt;
 *     Age: &lt;INPUT type="text" name="prs-age" /&gt;
 *     &lt;INPUT type="submit" value="Submit" /&gt;
 * &lt;/FORM&gt;
 * </pre>
 * 
 * Lime MVC has a conversion mechanism which can be easyly extended. You need to implement your custom converter itself and 
 * converter's factory. Converter implementation takes a values from request and construct concrete instance which will be filled in 
 * into method's argument.
 * 
 * <p><pre class="prettyprint">
 * private static class PersonConverter implements ConversionService.Converter<Person> {
 *   
 *   public Person convert(String nameInForm, Map<String, String[]> data)
 *   {
 *      String personName = data.get(nameInForm + "-name")[0];
 *      String personSurname = data.get(nameInForm + "-surname")[0];
 *      int personAge = Integer.parse(data.get(nameInForm + "-age")[0]);        
 *      return new Person(personName, personSurname, personAge);
 *   }
 * }
 * </pre>
 * 
 * The convert() method's 'nameInForm' is filled in with value from the RequestParameter annotation and 'data' map contains the post data 
 * from the HTTP request. The Converter's factory makes the instance of converter for concrete argument in the beginning when the 
 * Lime MVC module is initialized. Usually the factory constructs the singletoned instance. Example of the exception is Date conversion, 
 * when is created instance of converter for each argument because the DateConv annotation or Boolean conversion when we can use the 
 * BooleanConv annotation.
 * 
 * <p><pre class="prettyprint">
 * public class PersonConverterFactory implements ConversionService.ConverterFactory {
 *    
 *    private final ConversionService.Converter<Person> converter = new PersonConverter();
 * 
 *    public ConversionService.Converter<?> createConverter(Class<?> argumentType, Annotation[] annotations) {
 *       if (type == Person.class) {
 *          return converter;
 *       }
 *       return null;
 *    }
 * }
 * </pre> 
 * 
 * Factory's createConverter method is called for each argument in each controller's method. When the type of argument is matching with the
 * factory's supported type, the instance of converted is returned. When the argument type is different, then createConverter returns null.
 * The annotations contains all argument's annotations.  You might define your custom annotations for argument and you can parametrize the
 * convertor via these annotations. As a example you can look into DateConverterFactory.
 * 
 * <p><p>
 * The last step is a registration of the converter. This happens when you call the registerConverter() method in your MvcModule's implementation.
 * 
 * <p><pre class="prettyprint">
 * public class MyModule extends new MvcModule() {
 *    protected void configureControllers() {
 *       registerConverter(new PersonConverterFactory());
 *       ...
 *    } 
 * }
 * </pre>
 * 
 */
package org.zdevra.guice.mvc.converters;