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
	public View scan(Annotation[] anots) throws Exception {
		ToFreemarkerView anot = Utils.getAnnotation(ToFreemarkerView.class, anots);
		if (anot == null) {
			return View.NULL_VIEW;
		}
		
		return new FreemarkerView(freemarkerConf, anot.value());
	}
	
// ------------------------------------------------------------------------

}
