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
import static org.easymock.EasyMock.verify;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

@Test
public class ExceptionResolverTest {
	
/*---------------------------- m. variables ----------------------------*/
	
	private ExceptionResolver resolver;	
	
/*----------------------------------------------------------------------*/
	
	public static class TestHandler implements ExceptionHandler
	{
		@Override
		public void handleException(Throwable t, HttpServlet servlet, HttpServletRequest req, HttpServletResponse resp) {			
			t.getMessage();
		}		
	}
	
/*------------------------------- methods ------------------------------*/
	
	@BeforeTest()
	public void prepareResolver() {
		Map<Class<? extends Throwable>, ExceptionHandler> handlers 
			= new HashMap<Class<? extends Throwable>, ExceptionHandler>();
		
		handlers.put(NullPointerException.class, new TestHandler() );
		handlers.put(IllegalStateException.class, new TestHandler() );
		handlers.put(OutOfMemoryError.class, new TestHandler() );
		handlers.put(IOException.class, new TestHandler() );
		handlers.put(Exception.class, new TestHandler() );				
		
		resolver = new DefaultExceptionResolver(handlers);
	}

	
	@Test
	public void testExceptionNpe() {
		NullPointerException e = createMock(NullPointerException.class);
		expect(e.getMessage()).andReturn("NPE").times(2);
		replay(e);
		
		resolver.handleException(e, null, null, null);
		verify(e);
	}
	

	@Test
	public void testExceptionState() {
		IllegalStateException e = createMock(IllegalStateException.class);
		expect(e.getMessage()).andReturn("STATE").times(2);
		replay(e);
		
		resolver.handleException(e, null, null, null);
		verify(e);		
	}
	
	
	@Test
	public void testExceptionOutOfMemory() {
		OutOfMemoryError e = createMock(OutOfMemoryError.class);
		expect(e.getMessage()).andReturn("OOM").times(1);
		replay(e);
		
		resolver.handleException(e, null, null, null);
		verify(e);				
	}
	
	
	@Test
	public void testExceptionIO() {
		IOException e = createMock(IOException.class);
		expect(e.getMessage()).andReturn("ARGUMENT").times(2);
		replay(e);
		
		resolver.handleException(e, null, null, null);
		verify(e);						
	}
	
	
	@Test
	public void testExceptionResolverOrder() {
		Injector injector =  Guice.createInjector(
				new MvcModule() {
					@Override
					protected void configureControllers() {
						
						this.exception(IllegalStateException.class)
							.handledBy(new ExceptionHandler() {					
								@Override
								public void handleException(Throwable t, HttpServlet servlet, HttpServletRequest req, HttpServletResponse resp) {
									System.out.println("Illegal exception is:" + t.getMessage());
								}
							});
						
						this.exception(Exception.class)
							.handledBy(new ExceptionHandler() {					
								@Override
								public void handleException(Throwable t, HttpServlet servlet, HttpServletRequest req, HttpServletResponse resp) {
									System.out.println("Normal exception is:" + t.getMessage());
								}
						});						
					}					
				}
		);
		
		ExceptionResolver exceptionResolver = injector.getInstance(DefaultExceptionResolver.class);
		Assert.assertNotNull(exceptionResolver);
		
		exceptionResolver.handleException(new IllegalStateException("test1"), null, null, null);
		exceptionResolver.handleException(new NullPointerException("test"), null, null, null);
	}
	
	
}
