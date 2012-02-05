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

import org.zdevra.guice.mvc.parameters.ParamProcessorFactory;
import org.zdevra.guice.mvc.parameters.ParamProcessorsService;

import com.google.inject.Binder;
import com.google.inject.multibindings.Multibinder;

/** 
 * The builder is responsible for building and
 * registering of parameter processors into
 * parameter processor service.
 * 
 * @see MvcModule
 * @see ParamProcessorFactory
 * @see ParamProcessorsService
 */
@Deprecated
class ParamProcessorBuilder {
	
// ------------------------------------------------------------------------
	
	private final Multibinder<ParamProcessorFactory> paramBinder;
	
// ------------------------------------------------------------------------
	
	public ParamProcessorBuilder(Binder binder) {
		this.paramBinder = 
				Multibinder.newSetBinder(binder, ParamProcessorFactory.class);
	}

	public void registerParamProc(Class<? extends ParamProcessorFactory> paramProcFactory) {
		paramBinder.addBinding().to(paramProcFactory);
	}
	
// ------------------------------------------------------------------------
}
