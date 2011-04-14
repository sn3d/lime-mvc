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

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class BasicTest extends IntegrationTest {	
	
// ------------------------------------------------------------------------
	
	@Test(threadPoolSize=5, invocationCount=50)	
	public void basicDepartmentTest() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		WebClient webClient = new WebClient();
		webClient.getCookieManager().clearCookies();
		
		HtmlPage page = webClient.getPage("http://localhost:7374/test/get/departments");
		String txt = page.asText();				
		
		Pattern pattern = Pattern.compile("Department is: ", Pattern.MULTILINE);
		Matcher m = pattern.matcher(txt);
		
		boolean res = m.find();
		Assert.assertTrue(res);				
	}
	
	
	@Test(threadPoolSize=5, invocationCount=50)
	public void basicEmployeeTest() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		WebClient webClient = new WebClient();
		webClient.getCookieManager().clearCookies();
		
		HtmlPage page = webClient.getPage("http://localhost:7374/test/get/employees/someDep");
		String body = page.asText();
		
		Pattern pattern = Pattern.compile("dep=someDep, name=Scott, surname=Hartnell", Pattern.MULTILINE);
		Matcher m = pattern.matcher(body);
		
		boolean res = m.find();
		Assert.assertTrue(res);
	}
	
	
	
	@Test(threadPoolSize=5, invocationCount=50)
	public void basicUrlParamsTest() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		WebClient webClient = new WebClient();
		webClient.getCookieManager().clearCookies();
		
		HtmlPage page = webClient.getPage("http://localhost:7374/test/get/catalog/someId?val1=1&val2=2");
		String body = page.asText();
		System.out.println("body:" + body);
		
		Pattern pattern = Pattern.compile("Catalog is: someId", Pattern.MULTILINE);
		Matcher m = pattern.matcher(body);
		boolean res = m.find();
		Assert.assertTrue(res);
		
		pattern = Pattern.compile("val1=1");
		m = pattern.matcher(body);
		res = m.find();		
		Assert.assertTrue(res);
	}
	

	@Test(threadPoolSize=5, invocationCount=50)
	public void incorrectUrlTest() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		WebClient webClient = new WebClient();
		webClient.getCookieManager().clearCookies();
		
		HtmlPage page = webClient.getPage("http://localhost:7374/test/get/wrong");
		String text = page.asText();
		int index = text.indexOf("ERROR");
		Assert.assertTrue(index >= 0);
	}

	
// ------------------------------------------------------------------------
}
