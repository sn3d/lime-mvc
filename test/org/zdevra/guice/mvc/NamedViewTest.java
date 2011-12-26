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

import org.testng.Assert;
import org.testng.annotations.Test;
import org.zdevra.guice.mvc.annotations.Controller;
import org.zdevra.guice.mvc.views.NamedView;

@Test
public class NamedViewTest {
	
	@Controller(view="test")
	public static class TestController {		
	}
	
	@Controller(view="")
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
