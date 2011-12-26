package org.zdevra.lime.examples.freemarker;

import java.util.ArrayList;
import java.util.List;

import org.zdevra.guice.mvc.annotations.Controller;
import org.zdevra.guice.mvc.annotations.Path;
import org.zdevra.guice.mvc.annotations.RequestParameter;
import org.zdevra.guice.mvc.freemarker.annotations.FreemarkerView;

import com.google.inject.servlet.SessionScoped;

@Controller
@SessionScoped
@FreemarkerView("main.ftl")
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
	@FreemarkerView("add.ftl")
	public String addName(@RequestParameter("name") String name) {
		if (this.names.size() < 5) {
			this.names.add(name);
			return "true";
		}
		return "false";
	}

}
