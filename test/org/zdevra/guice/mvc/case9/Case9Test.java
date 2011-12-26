package org.zdevra.guice.mvc.case9;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * This is integration test which testing behaviour in Jetty.
 * The test starts the jetty server under port 9191 and executes
 * several requests.
 * 
 */
public class Case9Test {

	//------------------------------------------------------------------------------------
	// m. variables
	//------------------------------------------------------------------------------------

	private Server server;
	private HttpClient client;

	//------------------------------------------------------------------------------------
	// setup
	//------------------------------------------------------------------------------------

	@BeforeClass
	public void setup() throws Exception 
	{
		client = new HttpClient();
		
		String webapp = "test/org/zdevra/guice/mvc/case9/webapp";
		System.setProperty("java.naming.factory.url.pkgs", "org.mortbay.naming");
		System.setProperty("java.naming.factory.initial", "org.mortbay.naming.InitialContextFactory");
		server = new Server(9191);
		
		WebAppContext context = new WebAppContext();
		context.setContextPath("/");		
		context.setResourceBase(webapp);
		context.setDescriptor(webapp + "/WEB-INF/web.xml");
		context.setParentLoaderPriority(true);
		
		server.setHandler(context);
		server.start();
	}
	
	
	@AfterClass
	public void down() throws Exception
	{
		server.stop();
	}
	
	
	//------------------------------------------------------------------------------------
	// tests
	//------------------------------------------------------------------------------------
	
	@Test
	public void testPeople() throws InterruptedException, HttpException, IOException
	{
		HttpMethod req = new GetMethod("http://localhost:9191/case9/people");
		client.executeMethod(req);
		String out = req.getResponseBodyAsString();		
		Assert.assertEquals("SUCCESS", out);
	}
	
	
	@Test
	public void testPeopleNew() throws InterruptedException, HttpException, IOException
	{
		HttpMethod req = new GetMethod("http://localhost:9191/case9/people/new");
		client.executeMethod(req);
		String out = req.getResponseBodyAsString();
		Assert.assertEquals("FORM", out);
	}

	
	@Test
	public void testPeopleNewSecond() throws InterruptedException, HttpException, IOException
	{
		HttpMethod req = new GetMethod("http://localhost:9191/case9/people/new/second");
		client.executeMethod(req);
		String out = req.getResponseBodyAsString();
		Assert.assertEquals("FORM 2", out);
	}
	
	
	@Test
	public void testGetPeople() throws InterruptedException, HttpException, IOException
	{
		HttpMethod req = new GetMethod("http://localhost:9191/case9/people/rest");
		client.executeMethod(req);
		String out = req.getResponseBodyAsString();
		Assert.assertEquals("FORM GET", out);
	}


	@Test
	public void testPostPeople() throws InterruptedException, HttpException, IOException
	{
		HttpMethod req = new PostMethod("http://localhost:9191/case9/people/rest");
		client.executeMethod(req);
		String out = req.getResponseBodyAsString();
		Assert.assertEquals("FORM POST", out);
	}
}
