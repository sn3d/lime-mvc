package org.zdevra.lime.examples.interceptors;

import org.zdevra.guice.mvc.annotations.Controller;
import org.zdevra.guice.mvc.annotations.Model;
import org.zdevra.guice.mvc.annotations.Path;
import org.zdevra.guice.mvc.freemarker.annotations.FreemarkerView;

@Controller
public class PublicController {
	
	@Path("/info")
	@Model("msg")
	@FreemarkerView("/WEB-INF/views/main.ftl")
	public String info()
	{
		return "I am public controller.";
	}

}
