package org.zdevra.guice.mvc.case4;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zdevra.guice.mvc.ExceptionHandler;

public class CustomExceptionHandler implements ExceptionHandler {

	@Override
	public void handleException(Throwable t, HttpServlet servlet, HttpServletRequest req, HttpServletResponse resp) {
		try {
			resp.getWriter().write("customized handler:" + t.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
