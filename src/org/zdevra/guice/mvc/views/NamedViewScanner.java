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
package org.zdevra.guice.mvc.views;

import java.lang.annotation.Annotation;

import org.zdevra.guice.mvc.Utils;
import org.zdevra.guice.mvc.ViewScanner;
import org.zdevra.guice.mvc.annotations.Controller;
import org.zdevra.guice.mvc.annotations.RequestMapping;
import org.zdevra.guice.mvc.annotations.View;

import com.google.inject.Singleton;

/**
 * This implementation of the ViewScanner provides
 * controller's and method's scanning of an annotations 
 * for {@link NamedView}.
 * 
 * This scanner goes though method's and controller's 
 * annotations, looking for {@literal @}Controller and 
 * {@literal @}RequestMapping annotations and from
 * 'View' parameter extracts a name of view.
 *
 */
@Singleton
public class NamedViewScanner implements ViewScanner {
	
// ------------------------------------------------------------------------
	
	@Override
	public org.zdevra.guice.mvc.View scan(Annotation[] annotations) {
		org.zdevra.guice.mvc.View view = lookForView(annotations);
		if (view == org.zdevra.guice.mvc.View.NULL_VIEW) {
			view = lookForRequestMapping(annotations);
			if(view == org.zdevra.guice.mvc.View.NULL_VIEW) {
				view = lookForController(annotations);
			}
		}
		return view;
	}
	
// ------------------------------------------------------------------------
	
	/**
	 * This method looks for {@link View} annotation
	 * and create {@link NamedView}
	 * 
	 * @param annotations
	 * @return Named view if annotation is presented, otherwise View.NULL_VIEW.
	 */
	private org.zdevra.guice.mvc.View lookForView(Annotation[] annotations) {
		View anot = Utils.getAnnotation(View.class, annotations);
		if (anot != null) {
			return NamedView.create(anot.value());
		}
		return org.zdevra.guice.mvc.View.NULL_VIEW;
	}
	
	
	/**
	 * This method looks for {@literal @}Controller's view
	 * 
	 * @param annotations
	 * @return
	 * 
	 * @see Controller
	 */
	private org.zdevra.guice.mvc.View lookForController(Annotation[] annotations) {
		Controller ant = Utils.getAnnotation(Controller.class, annotations);
		if (ant != null) {
			return NamedView.create(ant.view());
		}
		return org.zdevra.guice.mvc.View.NULL_VIEW;
	}
	
	
	/**
	 * This method looks for {@literal @}RequestMapping's View
	 * 
	 * @param annotations
	 * @return
	 * 
	 * @see RequestMapping
	 */	
	private org.zdevra.guice.mvc.View lookForRequestMapping(Annotation[] annotations) {
		RequestMapping ant = Utils.getAnnotation(RequestMapping.class, annotations);
		if (ant != null) {
			return NamedView.create(ant.toView());
		}
		return org.zdevra.guice.mvc.View.NULL_VIEW;		
	}

}
