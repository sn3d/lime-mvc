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
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

@Test
public class ParameterTest extends IntegrationTest {
	
	@Test
	public void testBooleanParam() throws FailingHttpStatusCodeException, MalformedURLException, IOException {		
		WebClient webClient = new WebClient();
		webClient.getCookieManager().clearCookies();

		HtmlPage page = webClient.getPage("http://localhost:7374/test/param/uriboolparam/true/N");
		String text = page.asText();
		
		int idx = text.indexOf("param1='true', param2='false'");
		Assert.assertTrue(idx >= 0);		
	}
	
	
	@Test
	public void testIntegerParam() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		WebClient webClient = new WebClient();
		webClient.getCookieManager().clearCookies();

		HtmlPage page = webClient.getPage("http://localhost:7374/test/param/uriintparam/123/456");
		String text = page.asText();

		int idx = text.indexOf("param1='123', param2='456'");
		Assert.assertTrue(idx >= 0);				 
	}
	
	
	@Test
	public void testDateParam() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		WebClient webClient = new WebClient();
		webClient.getCookieManager().clearCookies();

		HtmlPage page = webClient.getPage("http://localhost:7374/test/param/uridateparam/20110301/asd");
		String text = page.asText();

		int idx = text.indexOf("param1='2011-03-01', param2='2001-01-01'");
		Assert.assertTrue(idx >= 0);				
	}
	
	
	@Test
	public void testArrayParam() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		WebClient webClient = new WebClient();
		webClient.getCookieManager().clearCookies();
		
		List<NameValuePair> params = new LinkedList<NameValuePair>();
		params.add(new NameValuePair("sampleval[0]", "0"));
		params.add(new NameValuePair("sampleval[1]", "1"));
		params.add(new NameValuePair("sampleval[2]", "2"));
		WebRequest request = new WebRequest(new URL("http://localhost:7374/test/param/arrayparam"));
		request.setRequestParameters(params);
		
		HtmlPage page = webClient.getPage(request);
		String text = page.asText();

		int idx = text.indexOf("param1='0', param2='1', param3='2'");
		Assert.assertTrue(idx >= 0);						
	}

}
