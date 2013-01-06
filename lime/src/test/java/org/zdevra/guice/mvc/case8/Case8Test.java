package org.zdevra.guice.mvc.case8;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.zdevra.guice.mvc.AbstractTest;

import com.meterware.httpunit.WebResponse;

/**
 * A test tests the parameter conversions and custom param conversion as well.
 */
public class Case8Test extends AbstractTest {

	
	public Case8Test() {
		super(Case8Servlet.class);
	}
	
	
	@Test
	public void testNoInt() throws ServletException, IOException {		
		WebResponse resp = executeSimpleUrl("http://www.test.com/test/int/single");
		int code = resp.getResponseCode();
		Assert.assertTrue(code == HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
	
	
	@Test
	public void testInt() throws ServletException, IOException {
		Map<String, String[]> data = new HashMap<String, String[]>();
		data.put("number", new String[] { "1" });
		data.put("array",  new String[] { "1", "2" });

		WebResponse resp = executeFormularUrl("http://www.test.com/test/int/single", data);
		String out = resp.getText();
		System.out.println(out);
		Assert.assertTrue(out.contains("viewId=0 test message:ok"));
				
		resp = executeFormularUrl("http://www.test.com/test/int/array", data);
		out = resp.getText();
		System.out.println(out);
		Assert.assertTrue(out.contains("viewId=0 test message:ok"));
				
		resp = executeFormularUrl("http://www.test.com/test/integer/single", data);
		out = resp.getText();
		System.out.println(out);
		Assert.assertTrue(out.contains("viewId=0 test message:ok"));
				
		resp = executeFormularUrl("http://www.test.com/test/integer/array", data);
		out = resp.getText();
		System.out.println(out);
		Assert.assertTrue(out.contains("viewId=0 test message:ok"));

	}

    @Test
    public void testExplicitConverter() throws IOException, ServletException {
        Map<String, String[]> data = new HashMap<String, String[]>();
        data.put("person-name",     new String[] { "Peter" });
        data.put("person-surname",  new String[] { "Jacobs" });

        WebResponse resp = executeFormularUrl("http://www.test.com/test/person/one",     data);
        String out = resp.getText();
        System.out.println(out);
        Assert.assertTrue(out.contains("one: Person{name='1 Peter', surname='1 Jacobs'}"));

        resp = executeFormularUrl("http://www.test.com/test/person/two",     data);
        out = resp.getText();
        System.out.println(out);
        Assert.assertTrue(out.contains("two: Person{name='2 Peter', surname='2 Jacobs'}"));

        resp = executeFormularUrl("http://www.test.com/test/person/default", data);
        out = resp.getText();
        System.out.println(out);
        Assert.assertTrue(out.contains("default: Person{name='1 Peter', surname='1 Jacobs'}"));
    }

	
	
}
