package org.zdevra.guice.mvc.case11;

import org.zdevra.guice.mvc.annotations.Controller;
import org.zdevra.guice.mvc.annotations.Model;
import org.zdevra.guice.mvc.annotations.Path;
import org.zdevra.guice.mvc.annotations.RedirectView;

@Controller
public class Case11Controller 
{
	@Path("/absolute-redirect")
	@Model("param1")
	@RedirectView("http://www.google.com")
	public String doAbsoluteRedirect()
	{
		return "value1";
	}
	
	
	@Path("/relative-redirect")
	@Model("param1")
	@RedirectView(value="/welcome", contextRelative=false)
	public String doRelativeRedirect()
	{
		return "value1";
	}
	
	
	@Path("/relative-context")
	@Model("param2")
	@RedirectView("/welcome")
	public String doRelativeContextRedirect()
	{
		return "value2";
	}

	

}
