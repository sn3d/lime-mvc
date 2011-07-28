package org.zdevra.guice.mvc.case2;

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
 * This case tests the abstraction of controllers. In guice we may have split up 
 * a contoller's interface which defines a mapping and controller's 
 * implementation.
 */
@Test
public class Case2Test {

	private ServletRunner sr;

	@BeforeClass
	public void prepare() {
		sr = new ServletRunner();
		sr.registerServlet("test/*", Case2DispatcherServlet.class.getName());
	}
	
	@Test
	public void testSimple() throws IOException, ServletException {
		//prepare request
		ServletUnitClient sc = sr.newClient();
		WebRequest request   = new GetMethodWebRequest( "http://www.car.com/test/getcar/1999" );
		InvocationContext ic = sc.newInvocation( request );		
		
		//invoke request
		Servlet ss = ic.getServlet();
		ss.service(ic.getRequest(), ic.getResponse());			
		WebResponse response = ic.getServletResponse();
		
		//process response
		String out = response.getText();
		System.out.println(out);
		Assert.assertTrue( out.contains("ford-1999") );
	}
	

}
