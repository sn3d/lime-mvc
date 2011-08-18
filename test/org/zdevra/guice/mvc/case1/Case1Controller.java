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

import org.zdevra.guice.mvc.Controller;
import org.zdevra.guice.mvc.Model;
import org.zdevra.guice.mvc.RequestMapping;
import org.zdevra.guice.mvc.SessionParameter;

@Controller(sessionAttributes= { "book" }, toView="default")
@Singleton
public class Case1Controller {
	
	
	@RequestMapping(path="/do/simplecall", nameOfResult="testmsg")
	public String simpleCall() {
		return "simple call";
	}
	
	
	@RequestMapping(path="/do/exception")
	public void throwException() {
		throw new NullPointerException("Test exception");
	}
	
	
	@RequestMapping(path="/do/session")
	public Model getDataFromSession(
			@SessionParameter(value = "author") String author, 
			@SessionParameter("year") Integer year ) 
	{
		Model m = new Model();
		m.addObject("testmsg", author + " " + year);
		m.addObject("book", "Hamlet");
		return m;
	}
		
	@RequestMapping(path="/do/sessionmodel", nameOfResult="testmsg")
	public String getDataFromSessionAsModel(Model m) {
		String out = "in session is book:" + m.getObject("book");
		m.addObject("book", "Romeo&Juliette");
		return out;
	}
	
	
}
