package org.zdevra.guice.mvc;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class ModelTest {
	
	@Test
	public void testConstructor() {
		Model m = new Model("test1", "value1");
		String v = (String) m.getObject("test1");
		Assert.assertEquals("value1", v);
	}
	
}
