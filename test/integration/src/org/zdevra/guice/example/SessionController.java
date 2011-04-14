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
package org.zdevra.guice.example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.zdevra.guice.mvc.JspView;
import org.zdevra.guice.mvc.Controller;
import org.zdevra.guice.mvc.Model;
import org.zdevra.guice.mvc.ModelAndView;
import org.zdevra.guice.mvc.RequestMapping;
import org.zdevra.guice.mvc.SessionParameter;

import com.google.inject.servlet.SessionScoped;

@Controller(sessionAttributes={"autoincr1", "autoincr2", "autoincr3", "autoincr4"})
@SessionScoped
public class SessionController {
	
	private int autoincr4 = 0;
	
	@RequestMapping(path="/autoincrement/1", nameOfResult="autoincr1", toView="/autoincr.jsp")
	public int autoIncrement1(@SessionParameter("autoincr1") Integer oldValue) {
		if (oldValue == null) {
			oldValue = 0;
		}
		
		int newValue = oldValue + 1;
		return newValue;
	}
	
	
	@RequestMapping(path="/autoincrement/2", nameOfResult="autoincr2", toView="/autoincr.jsp")
	public Model autoIncrement2(Model m) 
	{
		Integer oldValue = (Integer)m.getObject("autoincr2");
		if (oldValue == null) {
			oldValue = 0;
		}
		
		int newValue = oldValue + 2;
		m.addObject("autoincr2", newValue);
		return m;
	}
	
	
	@RequestMapping(path="/autoincrement/3")
	public ModelAndView autoIncrement3(HttpServletRequest req) {
		
		HttpSession sessn = req.getSession(true);
		Integer oldValue = (Integer) sessn.getAttribute("autoincr3");
		if (oldValue == null) {
			oldValue = 0;
		}
		
		int newValue = oldValue + 3;
		
		Model m = new Model();
		m.addObject("autoincr3", newValue);
		ModelAndView mav = new ModelAndView(m, JspView.create("/autoincr.jsp"));
		
		return mav;
	}


	@RequestMapping(path="/autoincrement/4", nameOfResult="autoincr4")
	public int autoIncrement4() {
		this.autoincr4 += 4;
		return this.autoincr4;
	}
}
