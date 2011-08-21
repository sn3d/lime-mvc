package org.zdevra.guice.mvc.jsilver;

import javax.servlet.ServletContext;

import com.google.clearsilver.jsilver.resourceloader.FileSystemResourceLoader;

public class ServletContextResourceLoader extends FileSystemResourceLoader {

	public ServletContextResourceLoader(ServletContext context) {
		super(context.getRealPath("/"));
	}
	

	public ServletContextResourceLoader(ServletContext context, String webappDir ) {
		super(context.getRealPath(webappDir));
	}
	
}
