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
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.zdevra.guice.mvc.ConversionService.Convertor;

public class MethodParamsBuilder {
	
// ------------------------------------------------------------------------
		
	private final ConversionService conversionService;
	private final Method method;
	private List<ParamBuilder> paramBuilders;
	
// ------------------------------------------------------------------------
	public MethodParamsBuilder(Method method) {
		this(method, new ConversionService());
	}
	
	/**
	 * Constructor
	 */
	public MethodParamsBuilder(Method method, ConversionService conversionService) 
	{	
		this.conversionService = conversionService;
		this.method = method;
		this.paramBuilders = Collections.unmodifiableList(scanMethod());
	}
	
// ------------------------------------------------------------------------	
	
	/**
	 * The Method go throught method's parameters and create list of builders where
	 * for each one builder is one parameter in method.
	 */
	private final List<ParamBuilder> scanMethod() 
	{		
		Annotation[][] annotations = method.getParameterAnnotations();		
		Class<?>[] types = method.getParameterTypes();
		List<ParamBuilder> builders = new ArrayList<ParamBuilder>(types.length);
		
		for (int i = 0; i < types.length; ++i) {
			ParamBuilder builder = chooseBuilderForParam(types[i], annotations[i]);
			builders.add(builder);
		}		
		
		return builders;
	}
	
	
	private final ParamBuilder chooseBuilderForParam(Class<?> type, Annotation[] annotations) {
		ParamBuilder out = null;				
		
		out = UriParamBuilder.createBuilder(type, annotations, conversionService);
		if (out != null) {
			return out;
		}
				
		out = RequestParamBuilder.createBuilder(type, annotations, conversionService);
		if (out != null) {
			return out;
		}
		
		out = RequestInstParamBuilder.createBuilder(type);
		if (out != null) {
			return out;
		}
				
		out = ModelParamBuilder.createBuilder(type); 
		if (out != null) {
			return out;
		}
		
		out = SessionParamBuilder.createBuilder(type, annotations);
		if (out != null) {
			return out;
		}

		return new DefaultParamBuilder();
	}
	

// ------------------------------------------------------------------------
	
	/**
	 * Method generate array of values which are used for invoking of controller's method 
	 */
	public Object[] getValues(InvokeData invokeData) 
	{
		int count = paramBuilders.size();
		int index = 0;
		Object[] out = new Object[count];
		
		for (ParamBuilder paramBuilder : this.paramBuilders) {
			out[index] = paramBuilder.getValue(invokeData);
			index++;
		}
				
		return out;
	}		

	
// ------------------------------------------------------------------------
	
	public static interface ParamBuilder {
		public Object  getValue(InvokeData data);
	}
	
	
	public static class DefaultParamBuilder implements ParamBuilder {
		public Object getValue(Matcher uriMatcher, HttpServletRequest request, HttpServletResponse response, Model model) {
			throw new IllegalStateException("Wrong URI mapping. Check if the controller's method is properly annotated. Focus on the method's arguments.");
		}

		@Override
		public Object getValue(InvokeData data) {
			throw new IllegalStateException("Wrong URI mapping. Check if the controller's method is properly annotated. Focus on the method's arguments.");
		}		
	}
	
// ------------------------------------------------------------------------
	
	/**
	 * Support builder for @UriParameter annotation.
	 * <p>
	 * 
	 * Url in @RequestMapping of method can be regular expression. @UriParameter
	 * take value of some regexp. group and put it into method's parameter. Allowed
	 * are only string parameters of method.
	 * <p>
	 * 
	 * for example:
	 * <pre><code>
	 * @RequestMapping("/controller/([a..z]+)/([0..9]+)"
	 * public void controllerMethod(@UriParameter(1) String param1, @UriParameter(2) param2) {
	 *    //param1 == value in group [a..z]+;
	 *    //param2 == value in group [0..9]+; 
	 * ...
	 * }
	 * </code><pre>
	 */
	public static class UriParamBuilder implements ParamBuilder {
		
		private final int group;
		private final Convertor convertor;
		
		private UriParamBuilder(UriParameter annotation, Convertor convertor) 
		{
			this.group = annotation.value();
			this.convertor = convertor;
		}


		public static ParamBuilder createBuilder(Class<?> paramType, Annotation[] paramAnnotations, ConversionService conversionService) 
		{			
			UriParameter annotation = Utils.getAnnotation(UriParameter.class, paramAnnotations);
			if (annotation == null) {
				return null;
			}
			
			Convertor typeConvertor = conversionService.getConvertor(paramType, paramAnnotations);						
			return new UriParamBuilder(annotation, typeConvertor);
		}
				
		
		public Object getValue(InvokeData data) 
		{
			String stringValue = data.getUriMatcher().group(group);
			Object convertedValue = convertor.convert(stringValue);
			return convertedValue;
		}		
	}
	
	
// ------------------------------------------------------------------------
	
	/**
	 * This builder is created when method's parameter is annotated by
	 * RequestParameter annotation and take value from request's 
	 * parameter.
	 * <p>
	 * 
	 * Example:
	 * <pre><code>
	 * @RequestMapping("/control");
	 * public void controllerMethod(@RequestParameter("val1") String val1) {
	 *    ...
	 * }
	 * </code><pre>
	 * 
	 * Example execute controllerMethod() method when "www.someplace.com/control?val1=somevalue" is
	 * requested and put 'somevalue' into val1 argument.
	 * <p>
	 * 
	 * The RequestParam supports arrays as well. Let's assume that we have input fields named like 
	 * param[0], param[1], param[2] which contains integer values, we might use following controlling method:
	 * 
	 * <pre><code>
	 * @RequestMapping("/control");
	 * public void controllerMethod(@RequestParameter("param") int[] values) {
	 *    ...
	 * }
	 * </code><pre>
	 *   
	 */
	public static class RequestParamBuilder implements ParamBuilder {
		
		private final String requestName;
		private final Convertor convertor;
		private final boolean isArray;
				
		
		private RequestParamBuilder(RequestParameter annotation, Convertor convertor, boolean isArray) {
			this.requestName = annotation.value();
			this.convertor = convertor;
			this.isArray = isArray;
		}
		
		
		public static ParamBuilder createBuilder(Class<?> paramType, Annotation[] paramAnnotations, ConversionService conversionService) {			
			RequestParameter annotation = Utils.getAnnotation(RequestParameter.class, paramAnnotations);
			if (annotation == null) {
				return null;
			}
			
			if (paramType.isArray()) {
				Convertor typeConvertor = conversionService.getConvertor(paramType.getComponentType(), paramAnnotations);
				return new RequestParamBuilder(annotation, typeConvertor, true);
			} else {
				Convertor typeConvertor = conversionService.getConvertor(paramType, paramAnnotations);
				return new RequestParamBuilder(annotation, typeConvertor, false);
			}						
		}
		

		public Object getValue(InvokeData data) {
			if (isArray) {				
				return getArrayValue(data.getRequest());
			} else {
				return getSimpleValue(data.getRequest());
			}
		}
		
		
		private Object getArrayValue(HttpServletRequest request) 
		{
			int index = 0;
			List<String> valuesArray = new LinkedList<String>();
			
			while (true) {
				String value = "";
				String reqNameWithIndex = requestName + "[" + index + "]";
				value = request.getParameter(reqNameWithIndex);
								
				if (value == null) {
					break;
				}
				
				valuesArray.add(value);				
				index++;
			}

			String[] values = valuesArray.toArray(new String[]{});
			Object convertedArray = convertor.convert(values);
			return convertedArray;
		}
		
		
		private Object getSimpleValue(HttpServletRequest request) 
		{
			String stringVal = request.getParameter(requestName);
			Object convertedVal = convertor.convert(stringVal); 
			return convertedVal;			
		}
	}
	
// ------------------------------------------------------------------------
	
	
	/**
	 * This param builder is created if method has parameter which is
	 * instance of HttpServletRequest and put request object into 
	 * this parameter
	 * <p>
	 * 
	 * for example:
	 * <pre><code>
	 * @RequestMapping("/control");
	 * public void controllerMethod(HttpServletRequest request) {
	 *    request.getParameter("param");
	 * }
	 * </code></pre>
	 */
	public static class RequestInstParamBuilder implements ParamBuilder {
		
		private RequestInstParamBuilder() {			
		}
			
		public static ParamBuilder createBuilder(Class<?> paramType) {
			if (paramType != HttpServletRequest.class) {
				return null;
			}			
			return new RequestInstParamBuilder();
		}
	
		public Object getValue(InvokeData data) {
			return data.getRequest();
		}
	}
	
// ------------------------------------------------------------------------
	
	/**
	 * This builder is created for {@link Model} parameter in method.
	 * 
	 * for example:
	 * <pre><code>
	 * @RequestMapping("/control");
	 * public void controllerMethod(Model model) {
	 *   ...
	 * }
	 * </code></pre>
	 */
	public static class ModelParamBuilder implements ParamBuilder {
		
		public static ParamBuilder createBuilder(Class<?> paramType) {
			if (paramType != Model.class) {
				return null;
			}			
			return new ModelParamBuilder();
		}
		
		public Object getValue(InvokeData data) {
			return data.getModel();
		}		
	}
	
// ------------------------------------------------------------------------
	
	/**
	 * This builder is associated with {@link SessionParameter} annotation
	 * and support building process of arguments with this annotation.
	 * <p>
	 * 
	 * for example:
	 * <pre><code>
	 *  @RequestMapping(path="/loggeduserinfo") 
	 *  public void getInfoAboutLoggedUser(@SessionParameter("loggeduser") Customer customer) {
	 *    ...			
	 *	}
	 * </code></pre>
	 * <p>
	 * 
	 * In this case, builder build the customer's argument and fill it with the value from the 
	 * session with the name 'loggeduser'. If this attribute doesn't exist in the session, then is 
	 * filled by null. In case when in 'loggeduser' session attribute is object
	 * which is not instance of customer, is throwed exception. 
	 * 
	 */
	public static class SessionParamBuilder implements ParamBuilder {
		
		private String nameInSession;
		private Class<?> paramType;		
		
		
		private SessionParamBuilder(String nameInSession, Class<?> type) {
			this.nameInSession = nameInSession;
			this.paramType = type;
		}
		
		
		public static SessionParamBuilder createBuilder(Class<?> paramType, Annotation[] paramAnnotations) {
			SessionParameter annotation = Utils.getAnnotation(SessionParameter.class, paramAnnotations);
			if (annotation == null) {
				return null;
			}						
			return new SessionParamBuilder(annotation.value(), paramType);
		}
		
		
		public Object getValue(InvokeData data) {
			HttpSession session = data.getRequest().getSession(true);
			Object obj = session.getAttribute(this.nameInSession);
			if (obj != null) {
				if (!paramType.isInstance(obj)) {
					throw new IllegalStateException("object in session is not " + paramType.getName() + " but is " + obj.getClass().getName());
				}
			}
			return obj;
		}		
	}
	
// ------------------------------------------------------------------------
}
