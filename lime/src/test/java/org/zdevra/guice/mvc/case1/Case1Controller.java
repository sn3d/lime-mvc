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
package org.zdevra.guice.mvc.case1;

import javax.inject.Singleton;

import org.zdevra.guice.mvc.annotations.Controller;
import org.zdevra.guice.mvc.annotations.Model;
import org.zdevra.guice.mvc.annotations.Path;
import org.zdevra.guice.mvc.annotations.SessionParameter;

@Controller(sessionAttributes= { "book" }, view="default")
@Singleton
public class Case1Controller {
	
	
	@Path("/do/simplecall") @Model("testmsg")
	public String simpleCall() {
		return "simple call";
	}
	
	
	@Path("/do/exception")
	public void throwException() {
		throw new NullPointerException("Test exception");
	}
	
	
	@Path("/do/session")
	public org.zdevra.guice.mvc.ModelMap getDataFromSession(
			@SessionParameter(value = "author") String author, 
			@SessionParameter("year") Integer year ) 
	{
		org.zdevra.guice.mvc.ModelMap m = new org.zdevra.guice.mvc.ModelMap();
		m.addObject("testmsg", author + " " + year);
		m.addObject("book", "Hamlet");
		return m;
	}
		
	@Path("/do/sessionmodel") @Model("testmsg")
	public String getDataFromSessionAsModel(org.zdevra.guice.mvc.ModelMap m) {
		String out = "in session is book:" + m.getObject("book");
		m.addObject("book", "Romeo&Juliette");
		return out;
	}
	
	
	@Path("/do/pathtest")
	public String testmsg() {
		return "default model name & path test done";
	}
	
	@Path("/do/modelnametest")
	@Model("testmsg")
	public String pathTest() {
		return "defined model name & path test done";
	}
	
}
