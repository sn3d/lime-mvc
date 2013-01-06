package org.zdevra.guice.mvc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Determines the priority for method and specify the order of methods invocation.
 * For instance you have 2 methods in chain for one URL:
 *
 * <pre>
 *    {@literal @}Path("/page/.*")
 *    public void common() {
 *       ...
 *    }
 *
 *    {@literal @}Path("/page/next")
 *    public void next() {
 *       ...
 *    }
 * </pre>
 *
 * AS default the methods will be invoked in random order. Let's take situation, you wish
 * to invoke the 'common' on first place. You have to specify the priority for method:
 *
 * <pre>
 *    {@literal @}Path("/page/.*")
 *    {@literal @}Priority(Priority.HIGH)
 *    public void common() {
 *       ...
 *    }
 * </pre>
 *
 * That annotation ensure the common() method will be invoked as first and all other methods
 * will continue after this method.
 *
 * @author Zdenko Vrabel (zdenko.vrabel@celum.com)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Priority {
	public static final int HIGHEST = Integer.MIN_VALUE;
	public static final int DEFAULT = 0;
	public static final int LOWEST  = Integer.MAX_VALUE;
	public int value();
}
