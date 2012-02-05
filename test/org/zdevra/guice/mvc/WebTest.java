package org.zdevra.guice.mvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * This test class provide testing on integration level where the tested
 * code is running under embedded jetty and requests are executed via 
 * commons HttpClient
 * 
 */
public abstract class WebTest {

	//------------------------------------------------------------------------------------
	// m. variables
	//------------------------------------------------------------------------------------

	private int          port = 9191;
	private List<Webapp> webapps = new ArrayList<Webapp>(10);
	
	private   Server       server;
	protected HttpClient   client;
	
	//------------------------------------------------------------------------------------
	// inner classes & structures
	//------------------------------------------------------------------------------------
	
	/**
	 * Simple structure represents the webapp
	 */
	private static class Webapp 
	{	
		public String webappPath;
		public String contextPath;
		
		private Webapp(String webappPath, String contextPath) 
		{
			this.webappPath = webappPath;
			this.contextPath = contextPath;
		}
	}
	
	//------------------------------------------------------------------------------------
	// setup
	//------------------------------------------------------------------------------------

	@BeforeClass
	public void setup() throws Exception 
	{
		setupWebserver();
		
		client = new HttpClient();
		
		System.setProperty("java.naming.factory.url.pkgs", "org.mortbay.naming");
		System.setProperty("java.naming.factory.initial", "org.mortbay.naming.InitialContextFactory");
		server = new Server(port);
		
		WebAppContext contexts[] = new WebAppContext[webapps.size()];
		for (int i = 0; i < webapps.size(); ++i) {
			Webapp app = webapps.get(i);
			WebAppContext context = new WebAppContext();
			context.setContextPath(app.contextPath);
			context.setResourceBase(app.webappPath);
			context.setDescriptor(app.webappPath + "/WEB-INF/web.xml");
			context.setParentLoaderPriority(true);
			contexts[i] = context;
		}
				
		server.setHandlers(contexts);
		server.start();
	}
	
	
	@AfterClass
	public void down() throws Exception
	{
		server.stop();
	}

	
	//------------------------------------------------------------------------------------
	// abstract methods
	//------------------------------------------------------------------------------------

	protected abstract void setupWebserver();
	
	//------------------------------------------------------------------------------------
	// methods
	//------------------------------------------------------------------------------------
	
	protected final void setPort(int port)
	{
		this.port = port;
	}
	
	
	protected final void addWebapp(String webappPath, String contextPath)
	{
		webapps.add(new Webapp(webappPath, contextPath));
	}
	
	
	public String doSimpleRequest(String url) throws HttpException, IOException
	{
		HttpMethod req = new GetMethod(url);
		client.executeMethod(req);
		String out = req.getResponseBodyAsString();
		return out;
	}
	
	
	

}
