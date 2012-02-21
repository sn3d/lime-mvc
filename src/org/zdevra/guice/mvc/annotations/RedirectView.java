package org.zdevra.guice.mvc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation determines that the view should redirect to some URL.
 * Model data will be used as a URL paramteres. 
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface RedirectView {
	public String value();	
	boolean contextRelative() default true;
}
