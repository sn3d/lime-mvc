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

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.zdevra.guice.mvc.Controller;
import org.zdevra.guice.mvc.MethodParamsBuilder;
import org.zdevra.guice.mvc.Model;
import org.zdevra.guice.mvc.RequestMapping;
import org.zdevra.guice.mvc.RequestParameter;
import org.zdevra.guice.mvc.SessionParameter;
import org.zdevra.guice.mvc.UriParameter;

@Test
public class MethodParamsBuilderTest {
	
	public static class Customer {		
	}

	@Controller()
	public static class ControllerParams {		

		@RequestMapping(path="/customer/(.*)/data")
		public void methodWithUri(@UriParameter(1) String customer) {			
		}
		
		
		@RequestMapping(path="/customer/data")
		public void methodWithRequest(@RequestParameter("customerId") String customer) {			
		}
		
		
		@RequestMapping(path="/customer/data")
		public void methodWithRequestInst(HttpServletRequest request) {			
		}
		
		@RequestMapping(path="/customer/model")
		public void methodWithModel(Model model) {			
		}
		
		@RequestMapping(path="customer/session") 
		public void methodWithSession(@SessionParameter("customerInSession") Customer customer) {			
		}
				
	};
	
	
	@Test
	public void testUriParam() throws SecurityException, NoSuchMethodException {
		
		Method method = ControllerParams.class.getDeclaredMethod("methodWithUri", String.class);				
	    MethodParamsBuilder builder = new MethodParamsBuilder(method, new ConversionService());
	    
	    Matcher matcher = Pattern.compile("/customer/(.*)/data").matcher("/customer/TEST/data");
	    matcher.find();
	    
	    InvokeData data = new InvokeData(matcher, null, null, null, null, null);
	    Object[] vals = builder.getValues(data);
	    
	    Assert.assertTrue(vals != null);	    
	    Assert.assertTrue(vals.length == 1);
	    
	    String param = (String) vals[0];
	    Assert.assertEquals(param, "TEST");	    
	}

	
	@Test
	public void testRequestParam() throws SecurityException, NoSuchMethodException {

		Method method = ControllerParams.class.getDeclaredMethod("methodWithRequest", String.class);				
	    MethodParamsBuilder builder = new MethodParamsBuilder(method);
	    
	    HttpServletRequest requestMock = createMock(HttpServletRequest.class);
	    expect(requestMock.getParameter("customerId")).andReturn("TESTID").anyTimes();
	    replay(requestMock);
		
	    InvokeData data = new InvokeData(null, requestMock, null, null, null, null);
	    Object[] vals = builder.getValues(data);

	    Assert.assertTrue(vals != null);	    
	    Assert.assertTrue(vals.length == 1);
	    
	    String param = (String) vals[0];
	    Assert.assertEquals(param, "TESTID");	    

	}
	
	
	@Test
	public void testRequestInstParam() throws SecurityException, NoSuchMethodException {

		Method method = ControllerParams.class.getDeclaredMethod("methodWithRequestInst", HttpServletRequest.class);				
	    MethodParamsBuilder builder = new MethodParamsBuilder(method);
	    
	    HttpServletRequest requestMock = createMock(HttpServletRequest.class);
	    replay(requestMock);
		
	    InvokeData data = new InvokeData(null, requestMock, null, null, null, null);
	    Object[] vals = builder.getValues(data);

	    Assert.assertTrue(vals != null);	    
	    Assert.assertTrue(vals.length == 1);
	    Assert.assertEquals(vals[0], requestMock);	    
	}
	
	
	@Test
	public void testModelParam() throws SecurityException, NoSuchMethodException {
		Method method = ControllerParams.class.getDeclaredMethod("methodWithModel", Model.class);				
	    MethodParamsBuilder builder = new MethodParamsBuilder(method);
	    

	    Model model = createMock(Model.class);
	    replay(model);
		
	    InvokeData data = new InvokeData(null, null, null, model, null, null);
	    Object[] vals = builder.getValues(data);
	    
	    Assert.assertTrue(vals != null);	    
	    Assert.assertTrue(vals.length == 1);
	    Assert.assertEquals(vals[0], model);	    	    	    
	}

	
	@Test
	public void testSessionParam() throws SecurityException, NoSuchMethodException {
				
		//prepare mocks
		HttpSession sessionMock = createMock(HttpSession.class);
		expect(sessionMock.getAttribute("customerInSession")).andReturn(new Customer()).anyTimes();
		
		HttpServletRequest requestMock = createMock(HttpServletRequest.class);		
		expect(requestMock.getSession(true)).andReturn(sessionMock).anyTimes();
		
		replay(sessionMock);
		replay(requestMock);
				
		//test
		Method method = ControllerParams.class.getDeclaredMethod("methodWithSession", Customer.class);
		MethodParamsBuilder builder = new MethodParamsBuilder(method);
		
		InvokeData data = new InvokeData(null, requestMock, null, null, null, null);
		Object[] vals = builder.getValues(data);
		
	    Assert.assertNotNull(vals);	    
	    Assert.assertTrue(vals.length == 1);
	    Assert.assertTrue(vals[0] instanceof Customer);
	}

}
