package org.zdevra.guice.mvc;

import javax.servlet.http.HttpServlet;

import com.google.inject.Binder;

/**
 * Class is representing data-structure used internally by Lime and
 * compose the all necessary data for servlet.
 */
abstract class ServletDefinition {

// ------------------------------------------------------------------------

// ------------------------------------------------------------------------
	
	private final String urlPattern;
	
// ------------------------------------------------------------------------
	
	public abstract HttpServlet createServlet(Binder binder);
	
// ------------------------------------------------------------------------
	
	public ServletDefinition(String urlPattern) {
		this.urlPattern = urlPattern;
	}
	
	
	public final String getUrlPattern() {
		return urlPattern;
	}
	
// ------------------------------------------------------------------------
}