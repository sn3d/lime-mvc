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

import com.google.inject.Injector;


/**
 * The class collects all data which may be important for 
 * one invocation of the method.
 */
public class InvokeData {
/*---------------------------- m. variables ----------------------------*/

	private Matcher uriMatcher;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Model model;
	private Object controller;
	private RequestType reqType;
	private Injector injector;

/*---------------------------- constructors ----------------------------*/

	/**
	 * Constructor
	 */
	public InvokeData(Matcher uriMatcher, HttpServletRequest request,
			HttpServletResponse response, Model model, Object controller, RequestType reqType, Injector injector) 
	{
		this(controller);
		this.uriMatcher = uriMatcher;
		this.request = request;
		this.response = response;
		this.model = model;
		this.reqType = reqType;
		this.injector = injector;
	}
	
	private InvokeData(Object controller) {
		this.controller = controller;
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

	public Injector getInjector() {
		return injector;
	}
	
/*----------------------------------------------------------------------*/
	
	public static class Builder {
		private InvokeData data;
		
		public Builder newInstance(Object controller) {
			data = new InvokeData(controller);
			return this;
		}
		
		public InvokeData get() {
			return data;
		}
		
		public Builder from(InvokeData copiedData) {
			data.controller = copiedData.controller;
			data.injector = copiedData.injector;
			data.model = copiedData.model;
			data.reqType = copiedData.reqType;
			data.request = copiedData.request;
			data.response = copiedData.response;
			data.uriMatcher = copiedData.uriMatcher;
			return this;
		}
		
		public Builder withUriMatcher(Matcher uriMatcher) {
			data.uriMatcher = uriMatcher;
			return this;
		}
		
		public Builder withHttpRequest(HttpServletRequest request) {
			data.request = request;
			return this;
		}
		
		public Builder withHttpResponse(HttpServletResponse response) {
			data.response = response;
			return this;			
		}
				
		public Builder withModel(Model model) {
			data.model = model;
			return this;
		}
				
		public Builder withRequestType(RequestType type) {
			data.reqType = type;
			return this;
		}
		
		public Builder withInjector(Injector injector) {
			data.injector = injector;
			return this;
		}		
	}
	
/*----------------------------------------------------------------------*/
}
