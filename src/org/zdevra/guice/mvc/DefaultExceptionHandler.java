/*****************************************************************************
 * Copyright 2011 Zdenko Vrabel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 *****************************************************************************/
package org.zdevra.guice.mvc;

import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Implementation of default exception handler. All unhandled  
 * exceptions are routed from {@link ExceptionResolver} to this handler.
 * 
 * @see ExceptionResolver
 * @see GuiceExceptionResolver 
 */
public class DefaultExceptionHandler implements ExceptionHandler {
	
	private static final Logger logger = Logger.getLogger(DefaultExceptionHandler.class.getName());

	@Override
	public void handleException(Throwable t, HttpServlet servlet, HttpServletRequest req, HttpServletResponse resp) {
		try {						
			logger.log(Level.SEVERE, "Unhandled exception caught by Lime default handler (" + t.getClass().getName() + ")" , t);
			
			String msg = t.getLocalizedMessage() == null ? "" : t.getLocalizedMessage();
			PrintWriter writer = resp.getWriter();
			
			writer.write(
					"<HTML><STYLE>body {font-family: Arial,Helvetica,sans-serif;} .stacktrace " +
			        "{background: #EEE; padding-left: 10px; padding-top: 5px; padding-bottom: 5px;" +
					"margin-top: 0px; margin-bottom: 10px; display: block; font-family: monospace; }" +
			        "</STYLE> <BODY> <H2>Unhandled exception caught (");
			
			writer.write(t.getClass().getName());			
			writer.write(")</H2><B>Message:</B>");		
			writer.write(msg);			
			writer.write("<BR><BR><B>Stack trace:</B><pre class=\"stacktrace\">\n");
			t.printStackTrace(writer);			
			writer.write("\n</pre>Lime MVC default exception handler</BODY></HTML>");
								
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in default handler", e);		
		}
	}

}
