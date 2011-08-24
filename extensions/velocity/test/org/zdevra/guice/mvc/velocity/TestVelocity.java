package org.zdevra.guice.mvc.velocity;

import javax.servlet.http.HttpServletRequest;

import junit.framework.Assert;

import org.apache.velocity.app.VelocityEngine;
import org.testng.annotations.Test;
 

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
	
}
