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
package org.zdevra.guice.mvc.case1;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.zdevra.guice.mvc.AbstractTest;
import org.zdevra.guice.mvc.TestServlet;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.InvocationContext;
import com.meterware.servletunit.ServletUnitClient;

/**
 * This case tests the basic functionality like invoking right method,
 * manipulation with session, exception handling etc..
 */
@Test
public class Case1Test extends AbstractTest {
	
	public static class Case1Servlet extends TestServlet {
		public Case1Servlet() {
			super(Case1Controller.class, new Case1Module() );
		}	
	}
		
	public Case1Test() {
		super(Case1Servlet.class);
	}
	
		
	@Test
	public void testSimpleRequest() throws IOException, ServletException {
		//prepare request
		ServletUnitClient sc = sr.newClient();
		WebRequest request   = new GetMethodWebRequest( "http://www.bookstore.com/test/do/simplecall" );
		InvocationContext ic = sc.newInvocation( request );		
		
		//invoke request
		Servlet ss = ic.getServlet();
		ss.service(ic.getRequest(), ic.getResponse());			
		WebResponse response = ic.getServletResponse();
		
		//process response
		String out = response.getText();
		Assert.assertTrue( out.contains("simple call") );
	}
	
	
	@Test
	public void testException() throws IOException, ServletException {
		//prepare request
		ServletUnitClient sc = sr.newClient();
		WebRequest request   = new GetMethodWebRequest( "http://www.bookstore.com/test/do/exception" );
		InvocationContext ic = sc.newInvocation( request );
		
		//invoke request
		Servlet ss = ic.getServlet();
		ss.service(ic.getRequest(), ic.getResponse());			
		WebResponse response = ic.getServletResponse();
		
		//process response
		String out = response.getText();
		Assert.assertTrue(out.length() > 0);		
	}
	

	@Test
	public void testFromSession() throws IOException, ServletException {
		//prepare request
		ServletUnitClient sc = sr.newClient();
		WebRequest request   = new GetMethodWebRequest( "http://www.bookstore.com/test/do/session" );
		InvocationContext ic = sc.newInvocation( request );		
		ic.getRequest().getSession(true).setAttribute("author", "Shakespeare");
		ic.getRequest().getSession(true).setAttribute("year",   1564);
		
		//invoke request
		Servlet ss = ic.getServlet();
		ss.service(ic.getRequest(), ic.getResponse());			
		WebResponse response = ic.getServletResponse();

		//process response
		String out = response.getText();
		Assert.assertTrue( out.contains("Shakespeare 1564"));
		
		String book = (String)ic.getRequest().getSession().getAttribute("book");
		Assert.assertTrue( book.contains("Hamlet"));
	}
	
	
	@Test
	public void testFromSessionModel() throws Exception {
		//prepare request
		ServletUnitClient sc = sr.newClient();
		WebRequest request   = new GetMethodWebRequest( "http://www.bookstore.com/test/do/sessionmodel" );
		InvocationContext ic = sc.newInvocation( request );		
		ic.getRequest().getSession(true).setAttribute("author", "Shakespeare");
		ic.getRequest().getSession(true).setAttribute("year",   1564);
		ic.getRequest().getSession(true).setAttribute("book",   "Hamlet");

		//invoke request
		Servlet ss = ic.getServlet();
		ss.service(ic.getRequest(), ic.getResponse());			
		WebResponse response = ic.getServletResponse();

		//process response
		String out = response.getText();
		String sessionOut = (String)ic.getRequest().getSession().getAttribute("book");
		
		System.out.println("out:" + out);
		System.out.println("sessionout:" + sessionOut);
	}
}
