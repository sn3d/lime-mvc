package org.zdevra.guice.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zdevra.guice.mvc.View;

public class TestView implements View {

	@Override
	public void redirectToView(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Object msg = request.getAttribute("testmsg");
		response.getWriter().write("test message:" + msg);
	}

}
