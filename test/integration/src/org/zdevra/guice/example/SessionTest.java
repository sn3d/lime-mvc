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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

@Test
public class SessionTest extends IntegrationTest {
/*----------------------------------------------------------------------*/
	
	@DataProvider(name="counters-data")
	public Object[][] countersData() {
		return new Object[][] {
			{"1"},{"2"},{"3"},{"4"},
		};
	}
	
/*----------------------------------------------------------------------*/
	
	
	@Test(dataProvider="counters-data")
	public void testAutoincrement(String counter) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		WebClient webClient = new WebClient();
		webClient.getCookieManager().clearCookies();

		callInOrder(webClient, counter, 1);
		callInOrder(webClient, counter, 2);
		
		//reset cookies
		webClient.getCookieManager().clearCookies();
		callInOrder(webClient, counter, 1);
	}
	
	
	private void callInOrder(WebClient webClient, String counter, int order) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		HtmlPage page = webClient.getPage("http://localhost:7374/test/session/autoincrement/" + counter);
		String txt = page.asText();				
		
		int value = order * Integer.parseInt(counter);
		
		String patternTxt = "autoincr" + counter + "=" + value;		
		Pattern pattern = Pattern.compile(patternTxt);
		Matcher m = pattern.matcher(txt);
		Assert.assertTrue(m.find());		
	}

/*----------------------------------------------------------------------*/
}
