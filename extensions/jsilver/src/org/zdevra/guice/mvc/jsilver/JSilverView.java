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
package org.zdevra.guice.mvc.jsilver;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zdevra.guice.mvc.View;
import org.zdevra.guice.mvc.exceptions.JSilverViewException;

import com.google.clearsilver.jsilver.JSilver;
import com.google.clearsilver.jsilver.data.Data;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * The view implementation provide rendering of HTML page
 * via JSIlver template engine.
 * 
 * If you want to return the instance of the view in your controller,
 * ensure that your MVC model is derived from {@link JSilverModel}
 *
 */
public class JSilverView implements View {

// ------------------------------------------------------------------------
	
	private final String viewFile;
	@Inject private JSilver jSilver;		
	@Inject private ModelService modelService;
	
// ------------------------------------------------------------------------
	
	/**
	 * Constructor
	 */
	public JSilverView(String file, Injector injector) 
	{
		this(
			file,
			(JSilver)injector.getInstance(JSilver.class),
			(ModelService)injector.getInstance(ModelService.class) );
	}

	
	/**
	 * Constructor used by {@link JSilverModule}
	 */
	public JSilverView(String file) 
	{	
		this.viewFile = file;
	}
		
	
	/**
	 * Constructor
	 * 
	 * @param jSilver
	 * @param viewFile
	 * @param modelName
	 */
	JSilverView(String viewFile, JSilver jSilver, ModelService modelService) 
	{
		this.jSilver = jSilver;
		this.viewFile = viewFile;
		this.modelService = modelService;
	}

// ------------------------------------------------------------------------

	@SuppressWarnings("unchecked")
	@Override
	public void render(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) 
	{		
		try {
			//prepare data
			Data data = jSilver.createData();
			List<String> attrNames = Collections.list(request.getAttributeNames());
			for (String attrName : attrNames) {
				Object attr = request.getAttribute(attrName);
				modelService.convert(attrName, attr, data);
			}
			
			//render view
			StringBuffer buf = new StringBuffer();
			jSilver.render(viewFile, data, buf);
			response.getOutputStream().write(buf.toString().getBytes());
			response.getOutputStream().flush();
		} catch (IOException e) {
			throw new JSilverViewException(viewFile, request, e);
		}
	}
	
// ------------------------------------------------------------------------
}
