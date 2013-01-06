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

/**
 * Class prepresents composition of view and data model. Your controller's 
 * method would return an object of this class with data and view.
 *
 * <p>example:
 * <pre class="prettyprint">
 * {@literal @}Controller
 * public class BookController  {
 *    
 *    {@literal @}Path("/home")
 *    public ModelAndView allBooks() {
 *       ModelMap m = ...;
 *       View  v = ...;
 *       ...
 *       return new ModelAndView(m, v);
 *    }
 * }
 * </pre>
 */
public class ModelAndView {

/*---------------------------- m. variables ----------------------------*/
	
	private ModelMap model;
	private ViewPoint view;
	
/*---------------------------- constructors ----------------------------*/
	
	/**
	 * constructs a ModelAndView object and as a view is set
	 * undefined view View.NULL_VIEW.
	 */
	public ModelAndView() {
		this(new ModelMap(), ViewPoint.NULL_VIEW);
	}

	/**
	 * constructs a ModelAndView object with empty model 
	 * and concrete view.
	 * @param view
	 */
	public ModelAndView(ViewPoint view) {
		this(new ModelMap(), view);
	}
	
	/**
	 * constructos a ModelAndView object with concrete model
	 * and view. 
	 * @param model
	 * @param view
	 */
	public ModelAndView(ModelMap model, ViewPoint view) {
		this.model = model;
		this.view = view;
	}
		
/*------------------------------- methods ------------------------------*/

	public ModelMap getModel() {
		return model;
	}

	
	public ViewPoint getView() {
		return view;
	}
	
	public void addModel(ModelMap m) {
		this.model.addModel(m);
	}
	
	
	public void addView(ViewPoint v) {
		if (v != ViewPoint.NULL_VIEW) { 
			this.view = v;
		}
	}	
	
	/**
	 * Method is used internally by {@link MvcDispatcherServlet}.
	 * @param mav
	 */
	void mergeModelAndView(ModelAndView mav) {
		this.addModel(mav.model);
		this.addView(mav.view);
	}
	
	
	
/*---------------------------------------------------------------------*/
}
