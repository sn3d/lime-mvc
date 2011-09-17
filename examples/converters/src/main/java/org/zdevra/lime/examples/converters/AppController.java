package org.zdevra.lime.examples.converters;

import org.zdevra.guice.mvc.Controller;
import org.zdevra.guice.mvc.ModelName;
import org.zdevra.guice.mvc.Path;
import org.zdevra.guice.mvc.RequestParameter;
import org.zdevra.guice.mvc.views.ToView;

@Controller
@ToView("main")
public class AppController {

    @Path("/") @ModelName("msg")
    public String main() {
        return "none";
    }

    @Path("/person/add") @ModelName("msg")
    public String addPerson( @RequestParameter("person") Person p) {
        return p.toString();
    }

}
