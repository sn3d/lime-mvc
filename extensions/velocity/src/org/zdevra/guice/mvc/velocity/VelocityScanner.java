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
package org.zdevra.guice.mvc.velocity;

import java.lang.annotation.Annotation;

import org.apache.velocity.app.VelocityEngine;
import org.zdevra.guice.mvc.Utils;
import org.zdevra.guice.mvc.View;
import org.zdevra.guice.mvc.ViewScanner;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The view scanner is looking for {@literal @}ToVelocityView annotation
 * in controller or in controller's method and creates the 
 * {@link VelocityView}'s instance
 * 
 * This is internal class which is invisible for normal usage.
 */
@Singleton
public class VelocityScanner implements ViewScanner {
	
// ------------------------------------------------------------------------
	
	private final VelocityEngine velocity;
		
// ------------------------------------------------------------------------
			
	@Inject
	public VelocityScanner(VelocityEngine velocity) {
		this.velocity = velocity;
	}
		
// ------------------------------------------------------------------------
	
	@Override
	public View scan(Annotation[] controllerAnotations) {
		ToVelocityView anot = Utils.getAnnotation(ToVelocityView.class, controllerAnotations);
		if (anot == null) {
			return View.NULL_VIEW;
		}		
		return new VelocityView(anot.value(), velocity);
	}
	
// ------------------------------------------------------------------------

}
