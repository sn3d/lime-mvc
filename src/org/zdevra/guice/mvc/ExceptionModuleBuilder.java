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

import java.util.HashMap;
import java.util.Map;

import org.zdevra.guice.mvc.MvcModule.ExceptionBindingBuilder;

public class ExceptionModuleBuilder {
/*---------------------------- m. variables ----------------------------*/
	
	private Map<Class<? extends Throwable>, ExceptionHandler> handlers;	
	
/*---------------------------- constructors ----------------------------*/

	public ExceptionModuleBuilder() 
	{
		this.handlers = 
			new HashMap<Class<? extends Throwable>, ExceptionHandler>();
	}
	
/*----------------------------------------------------------------------*/

	private class ExceptionBindingBuilderImpl implements ExceptionBindingBuilder
	{
		private Class<? extends Throwable> exceptionClass;
				
		private ExceptionBindingBuilderImpl(Class<? extends Throwable> exceptionClass) {
			this.exceptionClass = exceptionClass;
		}
		
		@Override
		public void handledBy(ExceptionHandler handler) {
			handlers.put(exceptionClass, handler);
		}

		@Override
		public void toView(View exceptionView) {
			this.handledBy(new ViewExceptionHandler(exceptionView));			
		}
				
	}

/*------------------------------- methods ------------------------------*/
	
	public ExceptionBindingBuilderImpl exception(Class<? extends Throwable> exceptionClass) 
	{
		return new ExceptionBindingBuilderImpl(exceptionClass);
	}
	
	
	ExceptionResolver build() {
		return new DefaultExceptionResolver(this.handlers);
	}

/*----------------------------------------------------------------------*/
}
