package org.zdevra.guice.mvc.jsilver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used in controller or in 
 * method aand  tells to Lime MVC we want to render 
 * produced data via JSilver's template.
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface ToJSilverView {
	public String value();
}
