package org.zdevra.guice.mvc.case9;

import javax.servlet.http.HttpServletRequest;

import org.zdevra.guice.mvc.annotations.Controller;
import org.zdevra.guice.mvc.annotations.GET;
import org.zdevra.guice.mvc.annotations.POST;
import org.zdevra.guice.mvc.annotations.Path;
import org.zdevra.guice.mvc.annotations.View;

import com.google.inject.Module;

@Controller
public class Case9Controller {

    @Path("/people/new/second")
    @View("/WEB-INF/view/form2.html.jsp")
    public Module anotherCall(HttpServletRequest request) {
        return null;
    }

    @Path("/people")
    @View("/WEB-INF/view/success.html.jsp")
    public Module submit(HttpServletRequest request) {
        return null;
    }

    @Path("/people/new")
    @View("/WEB-INF/view/form.html.jsp")
    public Module showForm() {
        return null;
    }

    @GET
    @Path("/people/rest")
    @View("/WEB-INF/view/form-get.html.jsp")
    public Module getPeople() {
        return null;
    }

    @POST
    @Path("/people/rest")
    @View("/WEB-INF/view/form-post.html.jsp")
    public Module postPeople() {
        return null;
    }
}
