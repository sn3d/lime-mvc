package org.zdevra.guice.mvc.case8;

import org.zdevra.guice.mvc.TestServlet;

/**
 * Derived servlet from MvcDispatcherServlet
 */
public class Case8Servlet extends TestServlet {

    public Case8Servlet() {
        super(Case8Controller.class, new Case8Module());
    }
}
