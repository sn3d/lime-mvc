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
package org.zdevra.guice.mvc.case3;

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
 * This test is testing view resolver functionality
 */
@Test
public class Case3Test extends AbstractTest {

	public static class Case3Servlet extends TestServlet {
		public Case3Servlet() {
			super(Case3Controller.class, new Case3Module() );
		}	
	}
	
	
	public Case3Test() {
		super(Case3Servlet.class);
	}
	
	
	@Test
	public void testDefaultView() throws IOException, ServletException {
		//prepare request
		ServletUnitClient sc = sr.newClient();
		WebRequest request   = new GetMethodWebRequest( "http://www.test.com/test/view/default" );
		InvocationContext ic = sc.newInvocation( request );		
		
		//invoke request
		Servlet ss = ic.getServlet();
		ss.service(ic.getRequest(), ic.getResponse());			
		WebResponse response = ic.getServletResponse();
		
		//process response
		String out = response.getText();
		Assert.assertTrue( out.contains("viewId=0") );
	}
	
	
	@Test
	public void testUnknownView() throws IOException, ServletException {
		//prepare request
		ServletUnitClient sc = sr.newClient();
		WebRequest request   = new GetMethodWebRequest( "http://www.test.com/test/view/unknown" );
		InvocationContext ic = sc.newInvocation( request );		
		
		//invoke request
		Servlet ss = ic.getServlet();
		ss.service(ic.getRequest(), ic.getResponse());			
		WebResponse response = ic.getServletResponse();
		
		//process response
		String out = response.getText();
		Assert.assertTrue( out.contains("Error on HTTP request: 404") );
	}
	
	
	@Test
	public void testView1() throws IOException, ServletException {
		//prepare request
		ServletUnitClient sc = sr.newClient();
		WebRequest request   = new GetMethodWebRequest( "http://www.test.com/test/view/1" );
		InvocationContext ic = sc.newInvocation( request );		
		
		//invoke request
		Servlet ss = ic.getServlet();
		ss.service(ic.getRequest(), ic.getResponse());			
		WebResponse response = ic.getServletResponse();
		
		//process response
		String out = response.getText();
		Assert.assertTrue( out.contains("viewId=1") );
	}
	
	
	@Test
	public void testView2() throws IOException, ServletException {
		//prepare request
		ServletUnitClient sc = sr.newClient();
		WebRequest request   = new GetMethodWebRequest( "http://www.test.com/test/view/2" );
		InvocationContext ic = sc.newInvocation( request );		
		
		//invoke request
		Servlet ss = ic.getServlet();
		ss.service(ic.getRequest(), ic.getResponse());			
		WebResponse response = ic.getServletResponse();
		
		//process response
		String out = response.getText();
		Assert.assertTrue( out.contains("viewId=2") );
	}
	
	
	@Test
	public void testView3() throws IOException, ServletException {
		//prepare request
		ServletUnitClient sc = sr.newClient();
		WebRequest request   = new GetMethodWebRequest( "http://www.test.com/test/view/3" );
		InvocationContext ic = sc.newInvocation( request );		
		
		//invoke request
		Servlet ss = ic.getServlet();
		ss.service(ic.getRequest(), ic.getResponse());			
		WebResponse response = ic.getServletResponse();
		
		//process response
		String out = response.getText();
		Assert.assertTrue( out.contains("viewId=3") );
	}
	
	
	@Test
	public void testView4() throws IOException, ServletException {
		//prepare request
		ServletUnitClient sc = sr.newClient();
		WebRequest request   = new GetMethodWebRequest( "http://www.test.com/test/view/4" );
		InvocationContext ic = sc.newInvocation( request );		
		
		//invoke request
		Servlet ss = ic.getServlet();
		ss.service(ic.getRequest(), ic.getResponse());			
		WebResponse response = ic.getServletResponse();
		
		//process response
		String out = response.getText();
		Assert.assertTrue( out.contains("viewId=4") );
	}

}
