package org.zdevra.guice.mvc.case1;

import org.zdevra.guice.mvc.MvcDispatcherServlet;
import org.zdevra.guice.mvc.TestView;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;

class BookstoreModule extends AbstractModule {

	@Override
	protected void configure() {
		
	}
	
}

/**
 * It's necessary to wrap the MvcDispatcherServlet because the HttpUnit 
 * can't register a servlet with non-default constructor.
 */
public class Case1DispatcherServlet extends MvcDispatcherServlet {
	
	public Case1DispatcherServlet() {		
		super(Case1Controller.class, 
		      new TestView(), 
		      Guice.createInjector(new BookstoreModule())
		); 
	}
}
