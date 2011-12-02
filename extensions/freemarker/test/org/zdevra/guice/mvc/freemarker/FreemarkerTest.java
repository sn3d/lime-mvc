package org.zdevra.guice.mvc.freemarker;

import javax.servlet.http.HttpServletRequest;

import junit.framework.Assert;

import org.testng.annotations.Test;
import org.zdevra.guice.mvc.TestModule;
import org.zdevra.guice.mvc.TestRequest;
import org.zdevra.guice.mvc.TestResponse;
import org.zdevra.guice.mvc.ViewModule;
import org.zdevra.guice.mvc.ViewResolver;
import org.zdevra.guice.mvc.views.NamedView;

import com.google.inject.Guice;
import com.google.inject.Injector;

class FreemarkerTestModule extends TestModule {

	@Override
	protected void configure() {
		super.configure();
		
		install(new FreemarkerModule());							
		install(new ViewModule() {
			@Override
			protected void configureViews() {
				bindViewName("testview").toViewInstance(new FreemarkerView("test.ftl"));
			}					
		});
	}	
}


@Test
public class FreemarkerTest {
	
	@Test
	public void testFreemarkerBasic() {
		Injector g = Guice.createInjector(new FreemarkerTestModule());
		
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
		System.out.println(output);
		Assert.assertEquals("Test template val1 and val2", output);
	}
	

}
