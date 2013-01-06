package org.zdevra.guice.mvc.exceptions;

import javax.servlet.http.HttpServletRequest;

/**
 * Caught when something wrong happens in 
 * JSilver view.
 */
public class JSilverViewException extends ViewException {

    public JSilverViewException(String viewId, HttpServletRequest request, Throwable e) {
        super(viewId, request, e);
    }
}
