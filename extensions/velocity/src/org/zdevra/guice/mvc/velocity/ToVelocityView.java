package org.zdevra.guice.mvc.velocity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used in controller and is
 * telling to Lime MVC that we want to render a 
 * produced data via Velocity's template.
 * <br>
 * <b>example:</b>
 * <pre class="prettyprint">
 * {@literal @}Controller
 * public class MyController {
 *    
 *    {@literal @}RequestMapping(path="/helloworld", nameOfResult="msg")
 *    {@literal @}ToVelocityView("view.velocity")
 *    public String helloWorld() {
 *       ...
 *    }
 * }
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface ToVelocityView {
	public String value();
}

