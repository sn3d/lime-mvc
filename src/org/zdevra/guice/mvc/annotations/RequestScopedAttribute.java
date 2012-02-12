package org.zdevra.guice.mvc.annotations;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation determines the method's argument will be filled-in
 * with value from request's attributes.
 * 
 * Let's assume we've got security interceptor which setup the 'USER' 
 * request attribute.
 * 
 * <pre class="prettyprint">
 *    User actualUser = ...;
 *    request.setAttribute("USER", actualUser);
 * </pre>
 * 
 * In our controller, we can fill-in the 'USER' value into method's attribute
 * easily:
 * 
 * <pre class="prettyprint">
 * {@literal @}Controller
 * class MyController {
 *    {@literal @}Path("/department");
 *    public String handleRequest( {@literal @}RequestScopedAttribute("USER") User actualUser ) {
 *    	return "user is:" + user;
 *    }
 * }
 * </pre>
 * 
 */
@Retention(RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
public @interface RequestScopedAttribute {
	String value();
}
