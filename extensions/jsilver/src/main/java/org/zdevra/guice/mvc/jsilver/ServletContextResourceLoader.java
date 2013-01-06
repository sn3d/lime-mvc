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

import javax.servlet.ServletContext;

import com.google.clearsilver.jsilver.resourceloader.FileSystemResourceLoader;

/**
 * The class provide loading of jsilver template files
 * from web app.
 */
class ServletContextResourceLoader extends FileSystemResourceLoader {

	public ServletContextResourceLoader(ServletContext context) {
		super(context.getRealPath("/"));
	}
	

	public ServletContextResourceLoader(ServletContext context, String webappDir ) {
		super(context.getRealPath(webappDir));
	}
	
}
