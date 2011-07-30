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

import org.zdevra.guice.mvc.MvcDispatcherServlet;
import org.zdevra.guice.mvc.TestView;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;

class BookstoreModule extends AbstractModule {

	@Override
	protected void configure() {
		
	}
	
}

/**
 * It's necessary to wrap the MvcDispatcherServlet because the HttpUnit 
 * can't register a servlet with non-default constructor.
 */
public class Case1DispatcherServlet extends MvcDispatcherServlet {
	
	public Case1DispatcherServlet() {		
		super(Case1Controller.class, 
		      new TestView("0"), 
		      Guice.createInjector(new BookstoreModule())
		); 
	}
}
