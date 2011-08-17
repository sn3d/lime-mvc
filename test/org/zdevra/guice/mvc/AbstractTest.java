package org.zdevra.guice.mvc;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.testng.annotations.BeforeClass;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.InvocationContext;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

/**
 * All case tests are extending this basic class. The class 
 * prepare the Lime MVC module and register the Lime MVC servlet 
 * dispatcher as well.
 *
 */
public class AbstractTest {
	
	private final String servletName;
	private final String servletClassName;
	
	protected ServletRunner sr;
	
	
	public AbstractTest(Class<? extends MvcDispatcherServlet> servletClass, String servletName) {
		this.servletClassName = servletClass.getName();
		this.servletName = servletName; 		
	}
	
	
	public AbstractTest(Class<? extends MvcDispatcherServlet> servletClass) {
		this(servletClass, "test/*");
	}
		

	@BeforeClass
	public void prepare() {
		sr = new ServletRunner();
		sr.registerServlet(servletName, servletClassName);
	}
	
	
	public WebResponse executeSimpleUrl(String url) throws ServletException, IOException {
		//prepare request
		ServletUnitClient sc = sr.newClient();
		WebRequest request   = new GetMethodWebRequest( url );
		InvocationContext ic = sc.newInvocation( request );		
		
		//invoke request
		Servlet ss = ic.getServlet();
		ss.service(ic.getRequest(), ic.getResponse());			
		WebResponse response = ic.getServletResponse();
				
		return response;
	}
}
