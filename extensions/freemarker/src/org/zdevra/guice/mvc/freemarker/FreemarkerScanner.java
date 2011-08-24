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
package org.zdevra.guice.mvc.freemarker;

import java.lang.annotation.Annotation;

import org.zdevra.guice.mvc.Utils;
import org.zdevra.guice.mvc.View;
import org.zdevra.guice.mvc.ViewScanner;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import freemarker.template.Configuration;

/**
 * The view scanner is looking for {@literal @}ToFreemarkerView annotation
 * in controller or in controller's method and creates the 
 * {@link FreemarkerView}r instance
 * 
 * This is internal class which is invisible for normal usage.
 */
@Singleton
class FreemarkerScanner implements ViewScanner {
	
// ------------------------------------------------------------------------
		
	private final Configuration freemarkerConf;
	
// ------------------------------------------------------------------------
		
	@Inject
	public FreemarkerScanner(Configuration configuration) {
		this.freemarkerConf = configuration;
	}
	
// ------------------------------------------------------------------------

	@Override
	public View scan(Annotation[] anots) {
		ToFreemarkerView anot = Utils.getAnnotation(ToFreemarkerView.class, anots);
		if (anot == null) {
			return View.NULL_VIEW;
		}		
		return new FreemarkerView(freemarkerConf, anot.value());
	}
	
// ------------------------------------------------------------------------

}
