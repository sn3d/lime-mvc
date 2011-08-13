package org.zdevra.guice.mvc.case5;

import org.zdevra.guice.mvc.Controller;
import org.zdevra.guice.mvc.RequestMapping;
import org.zdevra.guice.mvc.views.ToView;

@Controller @ToView("one.jsp")
public class Case5Controller {

	@RequestMapping(path = "/action/one", nameOfResult="testmsg")
	public String actionOne() {
		return "onedata";
	}

	
	@RequestMapping(path = "/action/two", toView="two.jsp", nameOfResult="testmsg")
	public String actionTwo() {
		return "twodata";
	}
	
	
	@RequestMapping(path = "/action/three", nameOfResult="testmsg") @ToView("three.jsp")
	public String actionThree() {
		return "threedata";
	}
	
	
	@RequestMapping(path = "/action/custom", nameOfResult="testmsg") @ToTestView
	public String actionCustom() {
		return "customdata";
	}

	
}
