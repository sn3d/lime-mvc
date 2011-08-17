package org.zdevra.guice.mvc.case6;

import org.zdevra.guice.mvc.Controller;
import org.zdevra.guice.mvc.RequestMapping;
import org.zdevra.guice.mvc.views.ToView;

@Controller
@ToView("people.jsp")
public class Case6ControllerPeople {
	
	@RequestMapping(path="/common", nameOfResult="msg1")
	public String commonMethod() {
		return "people common";
	}
	
	@RequestMapping(path="/people", nameOfResult="msg1")
	public String peopleMethod() {
		return "people method";
	}

}
