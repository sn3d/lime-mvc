package org.zdevra.guice.mvc.case11;

import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.zdevra.guice.mvc.WebTest;

public class Case11Test extends WebTest {
	
	//------------------------------------------------------------------------------------
	// setup
	//------------------------------------------------------------------------------------


	@Override
	protected void setupWebserver() 
	{
		setPort(9191);
		addWebapp("src/test/java/org/zdevra/guice/mvc/case11/webapp", "/webapp");
	}

	
	//------------------------------------------------------------------------------------
	// tests
	//------------------------------------------------------------------------------------
	
	
	@Test
	public void testRedirectAbsolute() throws HttpException, IOException
	{
		HttpMethod method = doRequest("http://localhost:9191/webapp/case11/absolute-redirect");
		
		int code = method.getStatusCode();
		Header location = method.getResponseHeader("Location");
		
		System.out.println("code:" + code);
		System.out.println("location:" + location.getValue());		
		Assert.assertEquals(code, 302);
		Assert.assertEquals(location.getValue(), "http://www.google.com?param1=value1");		
	}
	
	@Test
	public void testRedirectRelative() throws HttpException, IOException
	{
		HttpMethod method = doRequest("http://localhost:9191/webapp/case11/relative-redirect");
		
		int code = method.getStatusCode();
		Header location = method.getResponseHeader("Location");		
		
		Assert.assertEquals(code, 302);
		Assert.assertEquals(location.getValue(), "http://localhost:9191/welcome?param1=value1");		
	}
	
	
	@Test
	public void testRedirectContext() throws HttpException, IOException
	{
		HttpMethod method = doRequest("http://localhost:9191/webapp/case11/relative-context");		
		
		int code = method.getStatusCode();
		Header location = method.getResponseHeader("Location");
		
		Assert.assertEquals(code, 302);
		Assert.assertEquals(location.getValue(), "http://localhost:9191/webapp/case11/welcome?param2=value2");		

	}


	@Test
	public void testError() throws HttpException, IOException
	{
		HttpMethod method = doRequest("http://localhost:9191/webapp/case11/npe");

		int code = method.getStatusCode();
		String body = method.getResponseBodyAsString();

		Assert.assertEquals(code, 500);
		Assert.assertTrue(body.startsWith("<HTML>"));
	}

	@Test
	public void testNotFound() throws HttpException, IOException
	{
		HttpMethod method = doRequest("http://localhost:9191/webapp/case11/unknown");

		int code = method.getStatusCode();
		String body = method.getResponseBodyAsString();

		Assert.assertEquals(code, 404);
		Assert.assertTrue(body.startsWith("<HTML>"));
	}


}
