package org.zdevra.guice.mvc.case4;

import org.zdevra.guice.mvc.Controller;
import org.zdevra.guice.mvc.RequestMapping;

@Controller(toView="default")
public class Case4Controller {

	@RequestMapping(path="/expetion/npe")
	public void throwNpe() {
		throw new NullPointerException("");
	}
	
	@RequestMapping(path="/expetion/custom")
	public void throwCustom() {
		throw new CustomException();
	}
	
	@RequestMapping(path="/expetion/advancedcustom")
	public void throwAdvCustom() {
		throw new AdvancedCustomException();
	}
		
	@RequestMapping(path="/expetion/advancedhandledexception")
	public void throwAdvHandledException() {
		throw new AdvancedHandledException();
	}

}
