package org.zdevra.guice.mvc.case9;

import javax.servlet.http.HttpServletRequest;

import org.zdevra.guice.mvc.Controller;
import org.zdevra.guice.mvc.GET;
import org.zdevra.guice.mvc.POST;
import org.zdevra.guice.mvc.Path;
import org.zdevra.guice.mvc.views.ToView;

import com.google.inject.Module;

@Controller
public class Case9Controller 
{		
	@Path("/people/new/second")  @ToView("/WEB-INF/view/form2.html.jsp")
	public Module anotherCall(HttpServletRequest request)
	{		
		return null;
	}

	
	@Path("/people")  @ToView("/WEB-INF/view/success.html.jsp")
	public Module submit(HttpServletRequest request)
	{
		return null;
	}
	
	
	@Path("/people/new")  @ToView("/WEB-INF/view/form.html.jsp")
	public Module showForm()
	{
		return null;
	}
	
	
	@GET @Path("/people/rest")  @ToView("/WEB-INF/view/form-get.html.jsp")
	public Module getPeople()
	{
		return null;
	}
	
	@POST @Path("/people/rest")  @ToView("/WEB-INF/view/form-post.html.jsp")
	public Module postPeople()
	{
		return null;
	}
	

}
