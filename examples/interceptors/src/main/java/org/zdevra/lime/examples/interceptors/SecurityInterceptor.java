package org.zdevra.lime.examples.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zdevra.guice.mvc.AbstractInterceptorHandler;

public class SecurityInterceptor extends AbstractInterceptorHandler {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (("admin".equalsIgnoreCase(username))
                && ("pass123".equalsIgnoreCase(password))) {
            request.setAttribute("USER", "admin");
            return true;
        }

        response.setStatus(401);
        return false;
    }
}
