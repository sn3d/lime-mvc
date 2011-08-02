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

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zdevra.guice.mvc.exceptions.MethodInvokingException;


/**
 * Class process exception-throws in the controller and forwarding 
 * these exceptions into concrete registered {@link ExceptionHandler}.
 * <p>
 * 
 * Keep this class immutable and thread-safe.
 * 
 */
class DefaultExceptionResolver implements ExceptionResolver {
	
/*---------------------------- m. variables ----------------------------*/
	
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(DefaultExceptionResolver.class.getName());
	
	private final Map<Class<? extends Throwable>, ExceptionHandler> exceptionHandlers;
	private final ExceptionHandler defaultHandler = new DefaultExceptionHandler();
	
/*---------------------------- constructors ----------------------------*/
	
	/**
	 * Constructor for empty resolver
	 */
	public DefaultExceptionResolver() {	
		this.exceptionHandlers = Collections.emptyMap();
	}

	
	/**
	 * Constructor for resolver
	 */
	public DefaultExceptionResolver(final Map<Class<? extends Throwable>, ExceptionHandler> handlers) {
		this.exceptionHandlers = Collections.unmodifiableMap(handlers);
	}
	
/*------------------------------- methods ------------------------------*/
	
	/* (non-Javadoc)
	 * @see org.zdevra.guice.mvc.IExceptionResolver#handleException(java.lang.Throwable, javax.servlet.http.HttpServlet, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void handleException(Throwable t, HttpServlet servlet, HttpServletRequest req, HttpServletResponse resp) {
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
				boolean res = handler.handleException(t, servlet, req, resp);
				if (res) {
					return;
				} else {
					handledCount++;
				}
			}
		}
		
		//unhandlend exception is forwarded into default handler 
		if (handledCount == 0) {
			this.defaultHandler.handleException(t, servlet, req, resp);
		}
	}
}
