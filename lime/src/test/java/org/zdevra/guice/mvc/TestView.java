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

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestView implements ViewPoint {
// ------------------------------------------------------------------------
	
	private static final Logger logger = Logger.getLogger(TestView.class.getName());
	private final String id;
	
// ------------------------------------------------------------------------
	
		
	public TestView(String id) {
		this.id = id;
	}

// ------------------------------------------------------------------------
	
	@Override
	public void render(ModelMap model, HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)  {
		try {
			Object msg = request.getAttribute("testmsg");
			if (msg == null) {
				msg = request.getAttribute("msg");
			}
			response.getWriter().write("viewId=" + id + " test message:" + msg);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error in TestView", e);
		}
	}
	
// ------------------------------------------------------------------------

}
