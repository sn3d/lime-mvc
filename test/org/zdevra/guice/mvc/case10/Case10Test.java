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
		addWebapp("test/org/zdevra/guice/mvc/case10/webapp", "/");		
	}

	//------------------------------------------------------------------------------------
	// test
	//------------------------------------------------------------------------------------

	@Test
	public void testGlobalInterceptor() throws HttpException, IOException
	{
		Case10Log.getInstance().reset();
		String out = doSimpleRequest("http://localhost:9191/case10/do-something");
		
		Assert.assertNotNull(out);
		Assert.assertTrue(out.contains("OUT"));		
		
		Assert.assertTrue(Case10Log.getInstance().contains("preHandle executed") == 1);
		Assert.assertTrue(Case10Log.getInstance().contains("postHandle executed") == 1);
		Assert.assertTrue(Case10Log.getInstance().contains("afterCompletion executed") == 1);
	}
}
