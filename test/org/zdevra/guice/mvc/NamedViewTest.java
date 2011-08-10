package org.zdevra.guice.mvc;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.zdevra.guice.mvc.views.NamedView;

@Test
public class NamedViewTest {
	
	@Controller(toView="test")
	public static class TestController {		
	}
	
	@Controller(toView="")
	public static class TestControllerNull{		
	}
	
	@Test
	public void testControllerToView() {
		View view = NamedView.create(TestController.class);
		Assert.assertNotNull(view);
		Assert.assertEquals(view.toString(), "NamedView [name=test]");
	}
	
	@Test
	public void testControllerToViewNull() {
		View view = NamedView.create(TestControllerNull.class);
		View viewnull = View.NULL_VIEW;
		Assert.assertTrue( view == viewnull );
	}

}
