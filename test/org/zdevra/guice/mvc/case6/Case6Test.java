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
package org.zdevra.guice.mvc.case6;

import java.io.IOException;

import javax.servlet.ServletException;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.zdevra.guice.mvc.AbstractTest;

import com.meterware.httpunit.WebResponse;

/**
 * This is a test for multiple controllers for one dispatcher servlet
 */
@Test
public class Case6Test extends AbstractTest {

	public Case6Test() {
		super(Case6Servlet.class);
	}
		
	@Test
	public void testCommonMethod() throws IOException, ServletException {
		WebResponse resp = executeSimpleUrl("http://www.test.com/test/common");
		String out = resp.getText();		
		Assert.assertTrue(out.contains("msg1:people common msg2:cars common"));
	}
	

	@Test
	public void testCarsMethod() throws IOException, ServletException {
		WebResponse resp = executeSimpleUrl("http://www.test.com/test/cars");
		String out = resp.getText();
		Assert.assertTrue(out.contains("case6 view id:cars.jsp msg1:cars method msg2:null"));
	}
	
	
	@Test
	public void testPeopleMethod() throws IOException, ServletException {
		WebResponse resp = executeSimpleUrl("http://www.test.com/test/people");
		String out = resp.getText();
		Assert.assertTrue(out.contains("case6 view id:people.jsp msg1:people method msg2:null"));
	}


	@Test
	public void testUnknownMethod() throws IOException, ServletException {
		WebResponse resp = executeSimpleUrl("http://www.test.com/test/unknown");
		String out = resp.getText();		
		Assert.assertTrue(out.contains("Unhandled exception caught (org.zdevra.guice.mvc.exceptions.NoMethodInvoked)"));
	}

}
