package org.zdevra.guice.mvc;

import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;

import com.google.inject.Binder;

/**
 * The class represent definition of direct view in module's building process
 * and creates the {@link ViewServlet}.
 * 
 * This class is mainly used by {@link MvcModule}
 */

class DirectViewDefinition extends ServletDefinition {
	
// ------------------------------------------------------------------------
	
	private static final Logger logger = Logger.getLogger(DirectViewDefinition.class.getName());
	private final ViewPoint view;
	
// ------------------------------------------------------------------------
	
	/**
	 * Constructor
	 */
	public DirectViewDefinition(String urlPattern, ViewPoint view) {
		super(urlPattern);
		this.view = view;
	}
	
// ------------------------------------------------------------------------

	@Override
	public HttpServlet createServlet(Binder binder) {
		logger.info("for path '" + getUrlPattern() + "' should be registered follwing direct view " + this.view.toString());
		binder.requestInjection(view);
		return new ViewServlet(view);
	}
	
// ------------------------------------------------------------------------

}
