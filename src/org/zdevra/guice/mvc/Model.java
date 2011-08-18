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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Class represents a data model produced by the controllers.
 * 
 * <p>example:
 * <pre class="prettyprint">
 * {@literal @}Controller
 * class BookController {
 * 
 *    {@literal @}RequestMapping(...)
 *    public Model doSomeAtion() {
 *       ...
 *       Model m = new Model();
 *       m.addObject("user", user);
 *       m.addObject("books", books);
 *       return m;
 *    } 
 * }
 * 
 * </pre>
 */
public class Model {
	
// ------------------------------------------------------------------------
	
	private Map<String, Object> modelObjects;
	
// ------------------------------------------------------------------------
	
	/**
	 * Constructor
	 */
	public Model() {
		this.modelObjects = new HashMap<String, Object>();
	}
	
	/**
	 * Constructor creates the model with data
	 * @param name
	 * @param value
	 */
	public Model(String name, Object value) {
		this();
		addObject(name, value);
	}
	
// ------------------------------------------------------------------------
	
	/**
	 * Method merge two models. Basically method 
	 * add to the model all data from another model.  
	 */
	public void addModel(Model m) {
		this.modelObjects.putAll(m.modelObjects);
	}
	
	/**
	 * Method put the object into model under concrete 
	 * name.
	 * 
	 * @param name
	 * @param obj
	 */
	public void addObject(String name, Object obj) {
		this.modelObjects.put(name, obj);
	}
	
	
	/**
	 * Method returns the object for concrete name 
	 * @param name
	 * @return
	 */
	public Object getObject(String name) {
		return this.modelObjects.get(name);
	}

// ------------------------------------------------------------------------
	
	@Override
	public String toString() {
		return "Model [modelObjects=" + modelObjects + "]";
	}


	void getObjectsFromSession(String[] names, HttpSession session) {
		getObjectsFromSession(Arrays.asList(names), session);
	}

	
	void getObjectsFromSession(Collection<String> names, HttpSession session) {
		for (String name : names) {
			Object obj = session.getAttribute(name);
			addObject(name, obj);
		}
	}


	void moveObjectsToSession(String[] names, HttpSession session) {
		moveObjectsToSession(Arrays.asList(names), session);
	}

	
	void moveObjectsToSession(Collection<String> names, HttpSession session) {
		for (String name: names) {
			Object obj = modelObjects.get(name);
			session.setAttribute(name, obj);
			modelObjects.remove(name);
		}
	}
	
		
	void moveObjectsToRequestAttrs(HttpServletRequest request) {
		for (Entry<String, Object> entry : this.modelObjects.entrySet()) {
			request.setAttribute(entry.getKey(), entry.getValue());
		}
	}
	
	
// ------------------------------------------------------------------------
}
