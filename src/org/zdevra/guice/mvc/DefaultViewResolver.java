package org.zdevra.guice.mvc;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zdevra.guice.mvc.views.JspView;
import org.zdevra.guice.mvc.views.NamedView;

import com.google.inject.ConfigurationException;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

/**
 * Class provide default behaviour of a view's 
 * resolving.
 * 
 * Resolver resolves only {@link NamedView} views. All other views
 * are renderred normally. Named views are translated to concrete views.
 * In the MvcModule, you may define translation rule for each named view.
 * If there is no rule for named view, view is transformed to {@link JspView}.
 *   
 */
@Singleton
public class DefaultViewResolver implements ViewResolver {
	
// ------------------------------------------------------------------------
	
	@Inject
	private Injector injector;
	
// ------------------------------------------------------------------------

	@Override
	public void resolve(View view, HttpServlet servlet, HttpServletRequest req, HttpServletResponse resp) {
		if (view instanceof NamedView) {
			String viewName = ((NamedView)view).getName();
			try {
				view = injector.getInstance(Key.get(View.class, Names.named(viewName)));
			} catch (ConfigurationException e) {
				view = new JspView(viewName);
			}
		} 
		
		view.render(servlet, req, resp);	
	}
	
// ------------------------------------------------------------------------

}
