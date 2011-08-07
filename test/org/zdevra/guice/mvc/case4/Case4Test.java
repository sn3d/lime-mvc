package org.zdevra.guice.mvc.case4;

import java.io.IOException;

import javax.servlet.ServletException;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.zdevra.guice.mvc.AbstractTest;
import org.zdevra.guice.mvc.TestServlet;

import com.meterware.httpunit.WebResponse;

/**
 * This is a testing class for Exception handling mechanism. 
 */
@Test
public class Case4Test extends AbstractTest {
	
	public static class Case4Servlet extends TestServlet {
		public Case4Servlet() {
			super(Case4Controller.class, new Case4Module());
		}	
	}
	
	
	public Case4Test() {
		super(Case4Servlet.class);
	}
		
	@Test
	public void testDefaultHandler() throws ServletException, IOException {
		WebResponse resp = executeSimpleUrl("http://www.test.com/test/expetion/npe");
		String out = resp.getText();
		Assert.assertTrue(out.contains("Lime MVC default exception handler"));
	}
		
	@Test 
	public void testCustomHandler() throws ServletException, IOException {
		WebResponse resp = executeSimpleUrl("http://www.test.com/test/expetion/custom");
		String out = resp.getText();
		Assert.assertTrue(out.contains("customized handler:CustomException"));		
	}
	
	@Test 
	public void testAdvancedCustomHandler() throws ServletException, IOException {
		WebResponse resp = executeSimpleUrl("http://www.test.com/test/expetion/advancedcustom");
		String out = resp.getText();
		Assert.assertTrue(out.contains("customized handler:AdvancedCustomException->CustomException"));		
	}
		
	@Test
	public void testAdvancedHandled() throws ServletException, IOException {
		WebResponse resp = executeSimpleUrl("http://www.test.com/test/expetion/advancedhandledexception");
		String out = resp.getText();
		Assert.assertTrue(out.contains("AdvancedHandledException->CustomException"));		
	}


}
