package org.zdevra.guice.mvc.case3;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.InvocationContext;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;


/**
 * This test is testing view resolver functionality
 */
public class Case3Test {

	private ServletRunner sr;

	@BeforeClass
	public void prepare() {
		sr = new ServletRunner();
		sr.registerServlet("test/*", Case3DispatcherServlet.class.getName());
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
