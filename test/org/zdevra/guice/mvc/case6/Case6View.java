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
package org.zdevra.guice.mvc.case6;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zdevra.guice.mvc.ViewPoint;

public class Case6View implements ViewPoint {
	
	private final String id;
	
	public Case6View(String id) {
		this.id = id;
	}

	@Override
	public void render(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)  
	{
		try {
			Object msg1 = request.getAttribute("msg1");
			Object msg2 = request.getAttribute("msg2");
			response.getWriter().write("case6 view id:" + id + " msg1:" + msg1 + " msg2:" + msg2);
		} catch (IOException e) {
			//TODO:dopisat ViewException
		} 
	}

}
