package org.zdevra.guice.mvc.case6;

import java.io.IOException;

import javax.servlet.ServletException;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.zdevra.guice.mvc.AbstractTest;

import com.meterware.httpunit.WebResponse;

/**
 * This is a test for multiple controllers for one dispatcher servlet
 */
@Test
public class Case6Test extends AbstractTest {

	public Case6Test() {
		super(Case6Servlet.class);
	}
		
	@Test
	public void testCommonMethod() throws IOException, ServletException {
		WebResponse resp = executeSimpleUrl("http://www.test.com/test/common");
		String out = resp.getText();		
		Assert.assertTrue(out.contains("msg1:people common msg2:cars common"));
	}
	

	@Test
	public void testCarsMethod() throws IOException, ServletException {
		WebResponse resp = executeSimpleUrl("http://www.test.com/test/cars");
		String out = resp.getText();
		Assert.assertTrue(out.contains("case6 view id:cars.jsp msg1:cars method msg2:null"));
	}
	
	
	@Test
	public void testPeopleMethod() throws IOException, ServletException {
		WebResponse resp = executeSimpleUrl("http://www.test.com/test/people");
		String out = resp.getText();
		Assert.assertTrue(out.contains("case6 view id:people.jsp msg1:people method msg2:null"));
	}


	@Test
	public void testUnknownMethod() throws IOException, ServletException {
		WebResponse resp = executeSimpleUrl("http://www.test.com/test/unknown");
		String out = resp.getText();		
		Assert.assertTrue(out.contains("Unhandled exception caught (org.zdevra.guice.mvc.exceptions.NoMethodInvoked)"));
	}

}
