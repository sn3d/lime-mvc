package org.zdevra.guice.mvc.case10;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.httpclient.HttpException;
import org.testng.annotations.Test;
import org.zdevra.guice.mvc.WebTest;

/**
 * This test is testing the global and method interceptors. The test 
 * is running the model application which is using the interceptors and doing 
 * the test routines. 
 */
public class Case10Test extends WebTest {
	
	//------------------------------------------------------------------------------------
	// setup
	//------------------------------------------------------------------------------------
	
	@Override
	protected void setupWebserver() 
	{
		setPort(9191);
		addWebapp("src/test/java/org/zdevra/guice/mvc/case10/webapp", "/");		
	}

	//------------------------------------------------------------------------------------
	// test
	//------------------------------------------------------------------------------------

	@Test
	public void testGlobalSecurityInterceptor() throws HttpException, IOException
	{
		String out = doRequest("http://localhost:9191/case10/do-something?username=admin&password=pass123").getResponseBodyAsString();		
		Assert.assertTrue(out.contains("OUT"));
		
		int status = doRequest("http://localhost:9191/case10log/do-something?username=admin&password=none").getStatusCode();
		Assert.assertTrue(status == 401);		
	}
	
	
	@Test
	public void testLocalLogInterceptor() throws HttpException, IOException
	{
		Case10Log.getInstance().reset();		
		String out = doRequest("http://localhost:9191/case10/do-something?username=admin&password=pass123").getResponseBodyAsString();
		
		Assert.assertTrue(out.contains("OUT"));
		Assert.assertFalse(Case10Log.getInstance().contains("preHandle executed") == 1);
		Assert.assertFalse(Case10Log.getInstance().contains("postHandle executed") == 1);
		Assert.assertFalse(Case10Log.getInstance().contains("afterCompletion executed") == 1);
		
		
		Case10Log.getInstance().reset();
		out = doRequest("http://localhost:9191/case10log/do-something?username=admin&password=pass123").getResponseBodyAsString();
		
		Assert.assertTrue(out.contains("OUT"));
		Assert.assertTrue(Case10Log.getInstance().contains("preHandle executed") == 1);
		Assert.assertTrue(Case10Log.getInstance().contains("postHandle executed") == 1);
		Assert.assertTrue(Case10Log.getInstance().contains("afterCompletion executed") == 1);
		
				
		Case10Log.getInstance().reset();		
		out = doRequest("http://localhost:9191/case10after/do-something?username=admin&password=pass123").getResponseBodyAsString();
		
		Assert.assertTrue(out.contains("OUT"));
		Assert.assertFalse(Case10Log.getInstance().contains("preHandle executed") == 1);
		Assert.assertFalse(Case10Log.getInstance().contains("postHandle executed") == 1);
		Assert.assertFalse(Case10Log.getInstance().contains("afterCompletion executed") == 1);
	}

}
