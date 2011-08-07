package org.zdevra.guice.mvc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zdevra.guice.mvc.exceptions.MethodInvokingException;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

/**
 * Lime's Exception resolver implementation which resolves all throwed exceptions from controllers.
 * 
 * For all exceptions is used generally {@link DefaultExceptionHandler}. If you want to handle the
 * exception, just make binding between exception and your handler.
 * 
 * Example of custom exception handling.
 * <pre class="prettypring">
 * public void configureControllers() {
 *    ...
 *    bindException(Custom1Exception.class).toHandler(Custom1ExceptionHandler.class);
 *    bindException(Custom2Exception.class).toHandlerInstance( new Custom2ExceptionHandler() );
 *    ...
 * }
 * </pre>
 * 
 * Exception handling is executed simmilar as catching block. This means if Custom2Exception in the 
 * example will be derived from Custom1Exception, the resolver uses for Custom2 a first registered handler.
 * In this case Custom1ExceptionHandler.
 *
 */
@Singleton
public class GuiceExceptionResolver implements ExceptionResolver {
	
	
	@Inject
	private Injector injector;
	
	private final ExceptionHandler defaultHandler; 
	private final Collection<ExceptionBind> binds;
			
	
	@Inject 
	public GuiceExceptionResolver(Set<ExceptionBind> exceptionBinds, @Named(ExceptionResolver.DEFAULT_EXCEPTIONHANDLER_NAME) ExceptionHandler defaultHandler) {		
		List<ExceptionBind> bindsArray = new ArrayList<ExceptionBind>(exceptionBinds);
		Collections.sort(bindsArray);
		this.binds = Collections.unmodifiableCollection(bindsArray);
		this.defaultHandler = defaultHandler;
	}

	
	@Override
	public void handleException(Throwable t, HttpServlet servlet, HttpServletRequest req, HttpServletResponse resp) {
		if (t instanceof MethodInvokingException) {
			t = t.getCause();
		}
		
		for (ExceptionBind bind : binds) {
			if (bind.getExceptionClass().isInstance(t)) {
				bind.getHandler(injector).handleException(t, servlet, req, resp);
				return;
			}
		}		
		
		defaultHandler.handleException(t, servlet, req, resp);
	}

}
