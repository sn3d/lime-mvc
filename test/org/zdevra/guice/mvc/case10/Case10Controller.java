package org.zdevra.guice.mvc.case10;

import org.zdevra.guice.mvc.annotations.Controller;
import org.zdevra.guice.mvc.annotations.Model;
import org.zdevra.guice.mvc.annotations.Path;
import org.zdevra.guice.mvc.annotations.View;

@Controller
public class Case10Controller {
	
	@Path("/do-something") @Model("out") @View("/WEB-INF/views/main.html.jsp")
	public String doSomething() 
	{
		return "something";
	}

}
