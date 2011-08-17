package org.zdevra.guice.mvc.case6;

import org.zdevra.guice.mvc.TestServlet;

public class Case6Servlet extends TestServlet {

	public Case6Servlet() {
		super(
			new Class<?>[] { 
				Case6ControllerCars.class, 
				Case6ControllerPeople.class 
			},
			new Case6Module() 
		);
	}

}
