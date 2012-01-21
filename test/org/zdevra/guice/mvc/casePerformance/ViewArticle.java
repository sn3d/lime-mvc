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
package org.zdevra.guice.mvc.casePerformance;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zdevra.guice.mvc.ViewPoint;

public class ViewArticle implements ViewPoint {

	@Override
	public void render(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
	{		
		try {
			String article = (String) request.getAttribute("article");	
			response.getWriter().write("Article detail\n");
			response.getWriter().write("Article:" + article + "\n");
		} catch (IOException e) {
			//TODO: dopisat ViewException
		}
	}

}
