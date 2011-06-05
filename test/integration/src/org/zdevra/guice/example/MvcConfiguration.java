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

import org.zdevra.guice.mvc.JspView;
import org.zdevra.guice.mvc.MvcModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class MvcConfiguration extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		Injector injector =  Guice.createInjector(
			new MvcModule() {
				
				@Override
				protected void configureControllers() {					
					serve("/simpleservlet/*").with(SomeServlet.class);										
					
					control("/get/*")
						.withController(SimpleController.class)
						.toView(JspView.create("/test.jsp"))
						.set();
					
					control("/auth/*")
						.withController(AuthController.class)
						.toView(JspView.create("/some/servlet"))
						.set();
					
					control("/session/*")
						.withController(SessionController.class)
						.toView(JspView.create("/autoincr.jsp"))
						.set();
					
					control("/param/*")
						.withController(ParameterController.class)
						.toView(JspView.create("/param.jsp"))
						.set();

                    control("/simple/*")
                        .withController(SimpleController.class)
                        .toView(JspView.create("/test.jsp"))
                        .set();
				}				
			}
		);
		
		return injector;
	}

}
