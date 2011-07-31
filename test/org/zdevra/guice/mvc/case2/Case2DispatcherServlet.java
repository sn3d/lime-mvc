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
package org.zdevra.guice.mvc.case2;

import org.zdevra.guice.mvc.MvcDispatcherServlet;
import org.zdevra.guice.mvc.MvcModule;
import org.zdevra.guice.mvc.TestView;

import com.google.inject.Guice;

public class Case2DispatcherServlet  extends MvcDispatcherServlet {
	
	public Case2DispatcherServlet() {		
		super(Case2Controller.class, 
		      new TestView("0"), 
		      Guice.createInjector(
		    		  new MvcModule() {
						@Override
						protected void configureControllers() {
							bind(Case2Controller.class).to(Case2ControllerImpl.class);
						}
		    		  } 
		       ) 
		); 
	}

}
