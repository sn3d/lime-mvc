package org.zdevra.guice.mvc.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.zdevra.guice.mvc.exceptions.ViewException;

/**
 * Caught when something wrong happens in 
 * Velocity view.
 */
public class VelocityViewException extends ViewException {

    public VelocityViewException(String viewId, HttpServletRequest request, Throwable e) {
        super(viewId, request, e);
    }
}
