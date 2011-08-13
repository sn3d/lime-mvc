package org.zdevra.guice.mvc.case5;

import java.io.IOException;

import javax.servlet.ServletException;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.zdevra.guice.mvc.AbstractTest;
import org.zdevra.guice.mvc.TestServlet;

import com.meterware.httpunit.WebResponse;

/**
 * Testing of NamedView scanner and scanner service itself
 */
@Test
public class Case5Test extends AbstractTest {

	public static class Case5Servlet extends TestServlet {
		public Case5Servlet() {
			super(Case5Controller.class, new Case5Module() );
		}	
	}
		
	public Case5Test() {
		super(Case5Servlet.class);
	}
		
	@Test
	public void testDefaultView() throws IOException, ServletException {
		WebResponse resp = executeSimpleUrl("http://www.test.com/test/action/one");
		String out = resp.getText();
		Assert.assertTrue(out.contains("viewId=1 test message:onedata"));		
	}

	@Test
	public void testToViewParam() throws IOException, ServletException {
		WebResponse resp = executeSimpleUrl("http://www.test.com/test/action/two");
		String out = resp.getText();
		Assert.assertTrue(out.contains("viewId=2 test message:twodata"));		
	}
	
	@Test
	public void testToViewAnnotation() throws IOException, ServletException {
		WebResponse resp = executeSimpleUrl("http://www.test.com/test/action/three");
		String out = resp.getText();
		Assert.assertTrue(out.contains("viewId=3 test message:threedata"));		
	}
	
	
	@Test
	public void testCustomAnnotation() throws IOException, ServletException {
		WebResponse resp = executeSimpleUrl("http://www.test.com/test/action/custom");
		String out = resp.getText();
		Assert.assertTrue(out.contains("viewId=9 test message:customdata"));		
	}

}
