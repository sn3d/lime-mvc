package org.zdevra.guice.mvc.jsilver;

import java.lang.annotation.Annotation;

import org.zdevra.guice.mvc.Utils;
import org.zdevra.guice.mvc.View;
import org.zdevra.guice.mvc.ViewScanner;

import com.google.clearsilver.jsilver.JSilver;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The view scanner is looking for {@literal @}ToJSilverView annotation
 * in controller or in controller's method and creates the 
 * {@link JSilverView} instance
 * 
 * This is internal class which is invisible for normal usage.
 */
@Singleton
class JSilverScanner implements ViewScanner {
	
// ------------------------------------------------------------------------

	private final JSilver jSilver;
	private final ModelService modelService;

// ------------------------------------------------------------------------
	
	@Inject
	public JSilverScanner(JSilver jSilver, ModelService modelService) {
		this.jSilver = jSilver;
		this.modelService = modelService;
	}
	
// ------------------------------------------------------------------------
	
	@Override
	public View scan(Annotation[] controllerAnotations) 
		throws Exception 
	{
		ToJSilverView anot = Utils.getAnnotation(ToJSilverView.class, controllerAnotations);
		if (anot == null) {
			return View.NULL_VIEW;
		}
		return new JSilverView(anot.value(), jSilver, modelService);
	}
	
// ------------------------------------------------------------------------
}
