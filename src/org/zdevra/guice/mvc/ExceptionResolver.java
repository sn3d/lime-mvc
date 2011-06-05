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

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zdevra.guice.mvc.exceptions.MethodInvokingException;


/**
 * Class process exceptions throwed in the controller and forwarding 
 * these exceptions into concrete registered {@link ExceptionHandler}.
 * <p>
 * 
 * Keep this class mutable and thread-safe.
 * 
 */
public class ExceptionResolver {
	
/*---------------------------- m. variables ----------------------------*/
	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);
	private final Map<Class<? extends Throwable>, ExceptionHandler> exceptionHandlers;
	
/*---------------------------- constructors ----------------------------*/
	
	/**
	 * Constructor for empty resolver
	 */
	public ExceptionResolver() {	
		this.exceptionHandlers = Collections.emptyMap();
	}

	
	/**
	 * Constructor for resolver
	 */
	public ExceptionResolver(final Map<Class<? extends Throwable>, ExceptionHandler> handlers) {
		this.exceptionHandlers = Collections.unmodifiableMap(handlers);
	}
	
/*------------------------------- methods ------------------------------*/
	
	/**
	 * Method is invoked by MvcDispatcherServlet when controller
	 * throw some exception. This method go throught all registered
	 * exception handlers and invoke correct handlers for that exception.
	 */
	public void handleException(Throwable t, HttpServletRequest req, HttpServletResponse resp) {
		int handledCount = 0;

        if (t.getClass() == MethodInvokingException.class) {
            t = t.getCause();
        }
        
		this.exceptionHandlers.entrySet().iterator();		
		for (Entry<Class<? extends Throwable>, ExceptionHandler> entry : exceptionHandlers.entrySet()) {
			Class<? extends Throwable> clazz = entry.getKey();
			ExceptionHandler handler = entry.getValue();
			
			boolean isInstance = clazz.isInstance(t);
			if (isInstance) {
				handler.handleException(t, req, resp);
				handledCount++;
			}
		}
		
		if (handledCount == 0) {
			try {
				logger.error("Unhandled exception has been throwed (" + t.getClass().getName() + "). Please register handler for this exception." , t);
				resp.getOutputStream().println("<HTML><BODY>ERROR</BODY></HTML>");
			} catch (IOException e) {
				throw new IllegalStateException("No exception handler is registered.");
			}
		}
	}
}
