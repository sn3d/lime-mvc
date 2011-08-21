package org.zdevra.guice.mvc.freemarker;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * This annotation is used in controller and 
 * tells to Lime MVC we want to render produced
 * data via freemarker's template.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface ToFreemarkerView {
	public String value();
}
