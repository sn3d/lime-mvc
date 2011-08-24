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
	public View scan(Annotation[] controllerAnotations) throws Exception {
		ToVelocityView anot = Utils.getAnnotation(ToVelocityView.class, controllerAnotations);
		if (anot == null) {
			return View.NULL_VIEW;
		}		
		return new VelocityView(anot.value(), velocity);
	}
	
// ------------------------------------------------------------------------

}
