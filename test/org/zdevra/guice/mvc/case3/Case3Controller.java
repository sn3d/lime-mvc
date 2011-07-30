package org.zdevra.guice.mvc.case3;

import org.zdevra.guice.mvc.Controller;
import org.zdevra.guice.mvc.RequestMapping;
import org.zdevra.guice.mvc.TestView;
import org.zdevra.guice.mvc.View;
import org.zdevra.guice.mvc.views.NamedView;

@Controller
public class Case3Controller {
	
	@RequestMapping(path="/view/default")
	public void defaultView() {		
	}
	
	
	@RequestMapping(path="/view/unknown", toView="unknown")
	public void viewUnknown() {		
	}

	
	@RequestMapping(path="/view/1", toView="one")
	public void viewOne() {		
	}
	
	
	@RequestMapping(path="/view/2", toView="two")
	public void viewTwo() {
		
	}
	
	
	@RequestMapping(path="/view/3")
	public View viewThree() {
		return NamedView.create("three");
	}
	

	@RequestMapping(path="/view/4")
	public View viewFour() {
		return new TestView("4");
	}

}
