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
package org.zdevra.guice.mvc.case3;

import org.zdevra.guice.mvc.Controller;
import org.zdevra.guice.mvc.Path;
import org.zdevra.guice.mvc.RequestMapping;
import org.zdevra.guice.mvc.TestView;
import org.zdevra.guice.mvc.View;
import org.zdevra.guice.mvc.views.NamedView;
import org.zdevra.guice.mvc.views.ToView;

@Controller(toView="default")
public class Case3Controller {
	
	@RequestMapping(path="/view/default")
	public void defaultView() {		
	}
	
	
	@RequestMapping(path="/view/unknown", toView="unknown")
	public void viewUnknown() {		
	}

	
	@RequestMapping(path="/view/1", toView="one")
	public void viewOne() {		
	}
	
	
	@RequestMapping(path="/view/2", toView="two")
	public void viewTwo() {
		
	}
	
	
	@RequestMapping(path="/view/3")
	public View viewThree() {
		return NamedView.create("three");
	}
	

	@RequestMapping(path="/view/4")
	public View viewFour() {
		return new TestView("4");
	}
	
	@Path("/view/5") @ToView("five")
	public void viewFive() {
	}

}
