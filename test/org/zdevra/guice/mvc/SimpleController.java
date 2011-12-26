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

import javax.inject.Singleton;

import org.zdevra.guice.mvc.annotations.Controller;
import org.zdevra.guice.mvc.annotations.RequestMapping;
import org.zdevra.guice.mvc.annotations.UriParameter;

@Controller()
@Singleton
public class SimpleController {

	@RequestMapping(path="/someController/(.*)", requestType=HttpMethodType.GET)
	public void controllMethod1(@UriParameter(1) String param) {
		System.out.println("invoked with controllMethod1 param=" + param);
	}
	
	
	@RequestMapping(path="/someController/controllMethod2/(.*)/(.*)")
	public void controllMethod2(@UriParameter(1) String param1, @UriParameter(2) String param2) {
		System.out.println("invoked with controllMethod2 param1=" + param1 + " param2=" + param2);
	}
	
	
}
