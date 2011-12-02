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
package org.zdevra.guice.mvc.velocity;

import javax.servlet.http.HttpServletRequest;

import junit.framework.Assert;

import org.apache.velocity.app.VelocityEngine;
import org.testng.annotations.Test;
import org.zdevra.guice.mvc.TestModule;
import org.zdevra.guice.mvc.TestRequest;
import org.zdevra.guice.mvc.TestResponse;
import org.zdevra.guice.mvc.ViewModule;
import org.zdevra.guice.mvc.ViewResolver;
import org.zdevra.guice.mvc.views.NamedView;

import com.google.inject.Guice;
import com.google.inject.Injector;
 

@Test
public class TestVelocity {

	@Test
	public void testView() throws Exception {
		//prepare velocity engine and view
		VelocityEngine velocity = new VelocityEngine();		
		velocity.addProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");	    	    
	    velocity.init();

		VelocityView view = new VelocityView("test.velocity", velocity);
		
		//prepare req & resp and render the view
		HttpServletRequest req = 
				TestRequest.builder()
					.setAttribute("attr1", "val1")
					.setAttribute("attr2", "val2")
					.build();
		
		TestResponse resp = new TestResponse();
		view.render(null, req, resp);				
		String output = resp.getOutputAsStr();
		
		//evaluate the result
		Assert.assertEquals("Test template val1 and val2", output);
		System.out.println(output);
	}
	
	
	@Test
	public void testView2() throws Exception {
		
		//prepare guice
		Injector g = Guice.createInjector(new TestModule() {
			@Override
			protected void configure() {
				super.configure();
				
				install(new VelocityModule());							
				install(new ViewModule() {
					@Override
					protected void configureViews() {
						bindViewName("testview").toViewInstance(new VelocityView("test.velocity"));
					}					
				});
			}			
		});
		
		//prepare req & resp and render the view
		HttpServletRequest req = 
				TestRequest.builder()
					.setAttribute("attr1", "val1")
					.setAttribute("attr2", "val2")
					.build();
		
		TestResponse resp = new TestResponse();

		//execute view resolving&rendering
		ViewResolver vr = g.getInstance(ViewResolver.class);
		vr.resolve(NamedView.create("testview"), null, req, resp);
		String output = resp.getOutputAsStr();
		
		//evaluate the result
		Assert.assertEquals("Test template val1 and val2", output);
		System.out.println(output);
	}
	
}
