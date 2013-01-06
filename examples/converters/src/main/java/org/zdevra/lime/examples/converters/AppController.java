package org.zdevra.lime.examples.converters;

import org.zdevra.guice.mvc.annotations.Controller;
import org.zdevra.guice.mvc.annotations.Model;
import org.zdevra.guice.mvc.annotations.Path;
import org.zdevra.guice.mvc.annotations.RequestParameter;
import org.zdevra.guice.mvc.annotations.View;

@Controller
@View("main")
public class AppController {

    @Path("/")
    @Model("msg")
    public String main() {
        return "none";
    }

    @Path("/person/add")
    @Model("msg")
    public String addPerson(@RequestParameter("person") Person p) {
        return p.toString();
    }
}
