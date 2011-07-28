package org.zdevra.guice.mvc.case2;

import org.zdevra.guice.mvc.Controller;
import org.zdevra.guice.mvc.RequestMapping;
import org.zdevra.guice.mvc.UriParameter;

@Controller
public interface Case2Controller {

	@RequestMapping(path="/getcar/(.*)", nameOfResult="testmsg")
	public String getCar( @UriParameter(1) String value);
	
}
