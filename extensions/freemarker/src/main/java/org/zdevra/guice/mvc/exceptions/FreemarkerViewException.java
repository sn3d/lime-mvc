package org.zdevra.guice.mvc.exceptions;
import javax.servlet.http.HttpServletRequest;

import org.zdevra.guice.mvc.exceptions.ViewException;


/**
 * The exception is occured when something wrong happens
 * in the Freemarker view
 */
public class FreemarkerViewException extends ViewException {
	public FreemarkerViewException(String viewId, HttpServletRequest request,
			Throwable e) {
		super(viewId, request, e);
	}
}
