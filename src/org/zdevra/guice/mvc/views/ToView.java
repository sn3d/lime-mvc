package org.zdevra.guice.mvc.views;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for controller and controller's methods
 * provide information that the {@link NamedView} should
 * be used for controller or method.
 * 
 * This annotation is extracted 'toView' parameter
 * from {@link Controller} and {@link RequestMapping}.
 * 
 * The annotation is processed by {@link NamedViewScanner}
 *
 * @see NamedView
 * @see NamedViewScanner
 * @see ViewScannerService
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface ToView {
	public String value();
}
