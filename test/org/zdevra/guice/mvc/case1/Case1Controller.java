package org.zdevra.guice.mvc.case1;

import javax.inject.Singleton;

import org.zdevra.guice.mvc.Controller;
import org.zdevra.guice.mvc.Model;
import org.zdevra.guice.mvc.RequestMapping;
import org.zdevra.guice.mvc.SessionParameter;

@Controller(sessionAttributes= { "book" })
@Singleton
public class Case1Controller {
	
	
	@RequestMapping(path="/do/simplecall", nameOfResult="testmsg")
	public String simpleCall() {
		return "simple call";
	}
	
	
	@RequestMapping(path="/do/exception")
	public void throwException() {
		throw new NullPointerException("Test exception");
	}
	
	
	@RequestMapping(path="/do/session")
	public Model getDataFromSession(
			@SessionParameter(value = "author") String author, 
			@SessionParameter("year") Integer year ) 
	{
		Model m = new Model();
		m.addObject("testmsg", author + " " + year);
		m.addObject("book", "Hamlet");
		return m;
	}
	
	
}
