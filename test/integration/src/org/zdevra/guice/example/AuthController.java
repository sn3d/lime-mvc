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

import org.zdevra.guice.mvc.Controller;
import org.zdevra.guice.mvc.RequestMapping;
import org.zdevra.guice.mvc.UriParameter;

import com.google.inject.servlet.SessionScoped;


@Controller
@SessionScoped
public class AuthController {
	
	private boolean logged;
	
	public AuthController() {
		this.logged = false;
	}
		
	@RequestMapping(path="/login/([a-z]+)/([a-z]+)")
	public void login( @UriParameter(1) String user, @UriParameter(2) String pass) {
		logged = true;
	}
		
	public boolean isLogged() {
		return this.logged;
	}
}
