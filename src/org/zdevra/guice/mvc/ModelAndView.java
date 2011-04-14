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
package org.zdevra.guice.mvc;

public class ModelAndView {

/*---------------------------- m. variables ----------------------------*/
	
	private Model model;
	private View view;
	
/*---------------------------- constructors ----------------------------*/
	
	public ModelAndView() {
		this(new Model(), View.NULL_VIEW);
	}

	public ModelAndView(View view) {
		this(new Model(), view);
	}
	
	public ModelAndView(Model model, View view) {
		this.model = model;
		this.view = view;
	}
		
/*------------------------------- methods ------------------------------*/

	public Model getModel() {
		return model;
	}

	
	public View getView() {
		return view;
	}
	
	
	public void mergeModelAndView(ModelAndView mav) {
		this.addModel(mav.model);
		this.addView(mav.view);
	}
	
	
	public void addModel(Model m) {
		this.model.addModel(m);
	}
	
	
	public void addView(View v) {
		if (v != View.NULL_VIEW) { 
			this.view = v;
		}
	}
	
/*---------------------------------------------------------------------*/
}
