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

import org.zdevra.guice.mvc.views.JspView;

import com.google.inject.Binder;
import com.google.inject.name.Names;

class NamedViewBuilder {
// ------------------------------------------------------------------------
	
	private final Binder binder;
	
// ------------------------------------------------------------------------
	
	/**
	 * Constructor
	 */
	public NamedViewBuilder(Binder binder) {
		this.binder = binder;
	}
	
	
	public MvcModule.NamedViewBindingBuilder bindViewName(String viewName) {
		return new NamedViewBindingBudilerImpl(viewName);
	}
	
// ------------------------------------------------------------------------
	
	class NamedViewBindingBudilerImpl implements MvcModule.NamedViewBindingBuilder {
		
		private final String viewName;
		
		public NamedViewBindingBudilerImpl(String viewName) {
			this.viewName = viewName;
		}

		@Override
		public void toView(Class<? extends ViewPoint> viewClass) {			
			binder.bind(ViewPoint.class)
				.annotatedWith(Names.named(viewName))
				.to(viewClass);
		}

		@Override
		public void toViewInstance(ViewPoint view) {
			binder.bind(ViewPoint.class)
			.annotatedWith(Names.named(viewName))
			.toInstance(view);			
		}

		@Override
		public void toJsp(String pathToJsp) {
			binder.bind(ViewPoint.class)
			.annotatedWith(Names.named(viewName))
			.toInstance(new JspView(pathToJsp));						
		}
		
	}
	
// ------------------------------------------------------------------------

}
