package org.zdevra.lime.examples.freemarker;

import java.util.ArrayList;
import java.util.List;

import org.zdevra.guice.mvc.Controller;
import org.zdevra.guice.mvc.Path;
import org.zdevra.guice.mvc.RequestParameter;
import org.zdevra.guice.mvc.freemarker.ToFreemarkerView;

import com.google.inject.servlet.SessionScoped;

@Controller
@SessionScoped
@ToFreemarkerView("main.ftl")
public class FreemarkerExampleController {

	public List<String> names;
	
	/**
	 * Class is session scoped, that mean the each
	 * session has his own instance of class
	 */
	public FreemarkerExampleController() {
		this.names = new ArrayList<String>(5);
	}
	
	/**
	 * The method returns list of names
	 */
	@Path("/*")
	public List<String> names() {
		return this.names;
	}
	
	/**
	 * Add new name
	 * @param name
	 * @return
	 */
	@Path("/add")
	@ToFreemarkerView("add.ftl")
	public String addName(@RequestParameter("name") String name) {
		if (this.names.size() < 5) {
			this.names.add(name);
			return "true";
		}
		return "false";
	}

}
