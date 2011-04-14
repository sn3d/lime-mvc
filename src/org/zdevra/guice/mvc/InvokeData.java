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

import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * The class collects all data which may be important for 
 * one invocation of the method.
 */
public class InvokeData {
/*---------------------------- m. variables ----------------------------*/

	private final Matcher uriMatcher;
	private final HttpServletRequest request;
	private final HttpServletResponse response;
	private final Model model;
	private final Object controller;
	private final RequestType reqType;

/*---------------------------- constructors ----------------------------*/

	/**
	 * Constructor
	 */
	public InvokeData(Matcher uriMatcher, HttpServletRequest request,
			HttpServletResponse response, Model model, Object controller, RequestType reqType) 
	{
		super();
		this.uriMatcher = uriMatcher;
		this.request = request;
		this.response = response;
		this.model = model;
		this.controller = controller;
		this.reqType = reqType;
	}
	
/*-------------------------- getters/setters ---------------------------*/

	public Matcher getUriMatcher() {
		return uriMatcher;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public Model getModel() {
		return model;
	}

	public Object getController() {
		return controller;
	}

	public RequestType getReqType() {
		return reqType;
	}
	
	
	
/*----------------------------------------------------------------------*/
}
