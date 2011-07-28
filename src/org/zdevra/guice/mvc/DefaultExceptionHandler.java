package org.zdevra.guice.mvc;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Implementation of default exception handler. All unhandled  
 * exceptions are routed from ExceptionResolver to this handler.
 */
public class DefaultExceptionHandler extends ExceptionHandler {
	
	private static final Logger logger = Logger.getLogger(ExceptionResolver.class.getName());

	@Override
	public boolean handleException(Throwable t, HttpServlet servlet, HttpServletRequest req, HttpServletResponse resp) {
		try {			
			
			logger.log(Level.SEVERE, "Unhandled exception caught by Lime default handler (" + t.getClass().getName() + ")" , t);
			
			resp.getWriter().write(
					"<HTML><STYLE>body {font-family: Arial,Helvetica,sans-serif;} .stacktrace " +
			        "{background: #EEE; padding-left: 10px; padding-top: 5px; padding-bottom: 5px;" +
					"margin-top: 0px; margin-bottom: 10px; display: block; font-family: monospace; }" +
			        "</STYLE> <BODY> <H2>Unhandled exception caught (");
			
			resp.getWriter().write(t.getClass().getName());			
			resp.getWriter().write(")</H2><B>Message:</B>");			
			resp.getWriter().write(t.getLocalizedMessage());			
			resp.getWriter().write("<BR><BR><B>Stack trace:</B><pre class=\"stacktrace\">\n");
			t.printStackTrace(resp.getWriter());			
			resp.getWriter().write("\n</pre>Lime MVC default exception handler</BODY></HTML>");
						
			return false;		
		} catch (Exception e) {
			e.printStackTrace();
			return false;		
		}
	}

}
