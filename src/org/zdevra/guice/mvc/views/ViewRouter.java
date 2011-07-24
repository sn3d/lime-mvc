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
package org.zdevra.guice.mvc.views;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zdevra.guice.mvc.View;

/**
 * Router decides into which view is request routed. For deciding are used
 * associated rules to each view. If request is not matchong with any of the 
 * rules, then is used default view.
 * 
 * The router is immutable and is created via ViewRouter.Builder 
 */
public class ViewRouter implements View {
	
/*---------------------------- m. variables ----------------------------*/
	
	private final Map<ViewRouterRule, View> rulesWithView;
	private final View defaultView;
	
	
	/**
	 * Class building the router's instance
	 */
	public static class Builder {
		
		private View defaultView;
		private Map<ViewRouterRule, View> associations;
		
		public Builder() {
			associations = new HashMap<ViewRouterRule, View>();
		}
				
		public Builder defaultView(View defaultView) {
			this.defaultView = defaultView;
			return this;
		}
		
		public Builder rule(ViewRouterRule rule, View view) {
			this.associations.put(rule, view);
			return this;
		}
		
		public ViewRouter build() {
			return new ViewRouter(this.associations, this.defaultView);			
		}
		
	}
		
	
/*---------------------------- constructors ----------------------------*/
	
	/**
	 * Hidden constructor. The router is constructed via Builder.
	 */
	private ViewRouter(Map<ViewRouterRule, View> rulesWithViews, View defaultView) {
		this.rulesWithView = rulesWithViews;
		this.defaultView = defaultView;
	}

/*------------------------------- methods ------------------------------*/
	
	@Override
	public void redirectToView(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException 
	{
		for (Map.Entry<ViewRouterRule, View> entry : this.rulesWithView.entrySet()) {
			ViewRouterRule rule = entry.getKey();
			if (rule.check(request)) {
				View toView = entry.getValue();
				toView.redirectToView(servlet, request, response);
				return;
			}
		}
		
		this.defaultView.redirectToView(servlet, request, response);
	}
	
/*----------------------------------------------------------------------*/
}
