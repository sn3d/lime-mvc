package org.zdevra.guice.mvc;

import javax.servlet.http.HttpSession;

@Controller
public class HttpSessinonController {
	
	public String sessionMethod(HttpSession session) {
		return "sessionMethod:" + session.getAttribute("test");
	}
}
