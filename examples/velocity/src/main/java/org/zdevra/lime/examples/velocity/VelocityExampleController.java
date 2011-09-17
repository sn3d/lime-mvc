package org.zdevra.lime.examples.velocity;

import java.util.ArrayList;
import java.util.List;

import org.zdevra.guice.mvc.Controller;
import org.zdevra.guice.mvc.Path;
import org.zdevra.guice.mvc.RequestParameter;
import org.zdevra.guice.mvc.velocity.ToVelocityView;

import com.google.inject.servlet.SessionScoped;

@Controller
@SessionScoped
@ToVelocityView("main.v")
public class VelocityExampleController {
	
	public List<String> names;
	
	
	/**
	 * Class is session scoped, that mean the each
	 * session has his own instance of class
	 */
	public VelocityExampleController() {
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
	@ToVelocityView("add.v")
	public boolean addName(@RequestParameter("name") String name) {
		if (this.names.size() < 5) {
			this.names.add(name);
			return true;
		}
		return false;
	}
}
