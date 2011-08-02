package org.zdevra.guice.mvc.case4;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zdevra.guice.mvc.ExceptionHandler;

public class AdvancedHandler implements ExceptionHandler {

	@Override
	public boolean handleException(Throwable t, HttpServlet servlet, HttpServletRequest req, HttpServletResponse resp) {
		try {
			resp.getWriter().write("advanced handler:" + t.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

}
