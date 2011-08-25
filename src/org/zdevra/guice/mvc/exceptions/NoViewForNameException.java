package org.zdevra.guice.mvc.exceptions;

/**
 * Exception is throw when the controller select
 * view name but in the Mvc Module is no associated view
 * to this name.
 */
public class NoViewForNameException extends MvcException {
	public NoViewForNameException(String viewName) {
		super("No view is associated to the view name '" + viewName + "'");
	}
}
