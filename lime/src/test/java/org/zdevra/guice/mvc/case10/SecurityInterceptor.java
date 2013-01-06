package org.zdevra.guice.mvc.case10;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zdevra.guice.mvc.AbstractInterceptorHandler;
import org.zdevra.guice.mvc.InterceptorHandler;
import org.zdevra.guice.mvc.ModelAndView;

public class SecurityInterceptor extends AbstractInterceptorHandler {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (("admin".equalsIgnoreCase(username))
                && ("pass123".equalsIgnoreCase(password))) {
            request.setAttribute("USER", new User(username, password));
            return true;
        }

        response.setStatus(401);
        return false;
    }
}
