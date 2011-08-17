package org.zdevra.guice.mvc.case6;

import org.zdevra.guice.mvc.Controller;
import org.zdevra.guice.mvc.RequestMapping;
import org.zdevra.guice.mvc.views.ToView;

@Controller
@ToView("cars.jsp")
public class Case6ControllerCars {

	@RequestMapping(path="/common", nameOfResult="msg2")
	public String commonMethod() {
		return "cars common";
	}
		
	@RequestMapping(path="/cars", nameOfResult="msg1")
	public String carsMethod() {
		return "cars method";
	}

}
