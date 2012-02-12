package org.zdevra.guice.mvc.case10;

import org.zdevra.guice.mvc.annotations.Controller;
import org.zdevra.guice.mvc.annotations.Model;
import org.zdevra.guice.mvc.annotations.Path;
import org.zdevra.guice.mvc.annotations.RequestScopedAttribute;
import org.zdevra.guice.mvc.annotations.View;

@Controller
public class Case10Controller {
	
	@Path("/do-something") @Model("out") @View("/WEB-INF/views/main.html.jsp")
	public String doSomething(@RequestScopedAttribute("USER") User usr) 
	{
		if (usr == null) {
			throw new NullPointerException();
		}		
		return "something:" + usr.toString();
	}

}
