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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import junit.framework.Assert;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@Test
public class ControllerMethodInvokerTest {
	
	private SimpleController controller;
	private MethodInvoker invoker;
	
	@BeforeTest
	public void beforeTest() {
		controller = new SimpleController();
		Method method = SimpleController.class.getDeclaredMethods()[0];
		invoker = ControllerMethodInvoker.createInvoker(method, new MethodParamsBuilder(method));
		System.out.println("prepare");
	}
	
	
	@Test
	public void testSimpleController() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		
	    HttpServletRequest requestMock = createMock(HttpServletRequest.class);
	    expect(requestMock.getMethod()).andReturn("GET").anyTimes();
	    expect(requestMock.getServletPath()).andReturn("/someController/param1").anyTimes();
	    expect(requestMock.getPathInfo()).andReturn("/someController/param1").anyTimes();
	    replay(requestMock);
		
	    InvokeData data = new InvokeData(null, requestMock, null, null, controller, RequestType.GET);
		ModelAndView mav = invoker.invoke(data);
		Assert.assertNotNull(mav);				
	}
	
	
	@Test
	public void testRequestTypeFilter() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
						
		HttpServletRequest requestMock = createMock(HttpServletRequest.class);
	    expect(requestMock.getMethod()).andReturn("GET").anyTimes();
	    expect(requestMock.getServletPath()).andReturn("/someController/param1").anyTimes();
	    expect(requestMock.getPathInfo()).andReturn("/someController/param1").anyTimes();
	    replay(requestMock);

	    InvokeData data = new InvokeData(null, requestMock, null, null, controller, RequestType.GET);
	    ModelAndView mav = invoker.invoke(data);
	    Assert.assertNotNull(mav);
	    
		requestMock = createMock(HttpServletRequest.class);
	    expect(requestMock.getMethod()).andReturn("POST").anyTimes();
	    expect(requestMock.getServletPath()).andReturn("/someController/param1").anyTimes();
	    expect(requestMock.getPathInfo()).andReturn("/someController/param1").anyTimes();
	    replay(requestMock);
	    
	    data = new InvokeData(null, requestMock, null, null, controller, RequestType.GET);
	    mav = invoker.invoke(data);
	    Assert.assertNotNull(mav);
	}
}
