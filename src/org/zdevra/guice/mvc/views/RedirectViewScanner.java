package org.zdevra.guice.mvc.views;

import java.lang.annotation.Annotation;

import org.zdevra.guice.mvc.Utils;
import org.zdevra.guice.mvc.ViewPoint;
import org.zdevra.guice.mvc.ViewScanner;
import org.zdevra.guice.mvc.annotations.RedirectView;

/**
 * Scanner is looking for {@link RedirectView} annotation and creates initialized
 * instance of the {@link RedirectViewPoint} for annotation.
 * 
 */
public class RedirectViewScanner implements ViewScanner {
	
	//-----------------------------------------------------------------------------------------------------------
	// methods
	//-----------------------------------------------------------------------------------------------------------

	@Override
	public final ViewPoint scan(Annotation[] annotations) 
	{
		RedirectView redirectAnnotation = Utils.getAnnotation(RedirectView.class, annotations);
		if (redirectAnnotation == null) {
			return ViewPoint.NULL_VIEW;
		}		
		return new RedirectViewPoint(redirectAnnotation.value(), redirectAnnotation.contextRelative());
	}

}
