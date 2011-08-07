package org.zdevra.guice.mvc.exceptions;

/**
 * Exception is occurring in these parts, which should be removed in the next release.
 */
public class ObsoleteException extends RuntimeException {

	public ObsoleteException(String replace) {
		super("This part is obsolete. It should be removed in the next release. Use a newest " + replace);
	}
	
}
