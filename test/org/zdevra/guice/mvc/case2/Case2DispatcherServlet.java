package org.zdevra.guice.mvc.case2;

import org.zdevra.guice.mvc.MvcDispatcherServlet;
import org.zdevra.guice.mvc.TestView;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;

public class Case2DispatcherServlet  extends MvcDispatcherServlet {
	
	public Case2DispatcherServlet() {		
		super(Case2Controller.class, 
		      new TestView(), 
		      Guice.createInjector(
		    		  new AbstractModule() {
		    			  @Override
		    			  protected void configure() {		    				  
		    				  bind(Case2Controller.class).to(Case2ControllerImpl.class);
		    			  } } 
		       ) 
		); 
	}

}
