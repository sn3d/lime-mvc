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
package org.zdevra.guice.mvc.freemarker;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zdevra.guice.mvc.View;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * The view provide rendering of outptu HTML via Freemarker template
 * engine
 */
public class FreemarkerView implements View {

// ------------------------------------------------------------------------
	
	private final Configuration freemarkerConf;
	private final String templateName;
	
// ------------------------------------------------------------------------
	
	/**
	 * The constructor 
	 */
	public FreemarkerView(Configuration freemakerConf, String templateFileName) throws IOException {
		this.freemarkerConf = freemakerConf;
		this.templateName = templateFileName;
	}

			
// ------------------------------------------------------------------------
	
	@SuppressWarnings("unchecked")
	@Override
	public void render(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) 
			throws Exception 
	{
		//prepare data
		List<String> attrNames = Collections.list(request.getAttributeNames());
		Map<String, Object> data = new HashMap<String, Object>();
		for (String attrName : attrNames) {
			Object attr = request.getAttribute(attrName);
			data.put(attrName, attr);
		}
		
		Template template = freemarkerConf.getTemplate(templateName);
		
		//render the output
		ByteArrayOutputStream bout = new ByteArrayOutputStream(2048);		
		Writer out = new OutputStreamWriter(new BufferedOutputStream(bout));
		template.process(data, out);
		out.flush();
		response.getWriter().write(bout.toString());		
	}
	
// ------------------------------------------------------------------------

}
