package org.zdevra.guice.mvc.case6;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zdevra.guice.mvc.View;

public class Case6View implements View {
	
	private final String id;
	
	public Case6View(String id) {
		this.id = id;
	}

	@Override
	public void render(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) 
			throws Exception 
	{
		Object msg1 = request.getAttribute("msg1");
		Object msg2 = request.getAttribute("msg2");
		response.getWriter().write("case6 view id:" + id + " msg1:" + msg1 + " msg2:" + msg2);	
	}

}
