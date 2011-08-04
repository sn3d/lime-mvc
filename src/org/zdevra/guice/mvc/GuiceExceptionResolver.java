package org.zdevra.guice.mvc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class GuiceExceptionResolver implements ExceptionResolver {
	
	public static final String DEFAULT_EXCEPTIONHANDLER_NAME = "defaultExceptionHandler";
	
	@Inject
	private Injector injector;
	
	private final ExceptionHandler defaultHandler; 
	private final Collection<ExceptionBind> binds;
			
	
	@Inject 
	public GuiceExceptionResolver(Set<ExceptionBind> exceptionBinds, @Named(DEFAULT_EXCEPTIONHANDLER_NAME) ExceptionHandler defaultHandler) {		
		List<ExceptionBind> bindsArray = new ArrayList<ExceptionBind>(exceptionBinds);
		Collections.sort(bindsArray);
		this.binds = Collections.unmodifiableCollection(bindsArray);
		this.defaultHandler = defaultHandler;
	}

	
	@Override
	public void handleException(Throwable t, HttpServlet servlet, HttpServletRequest req, HttpServletResponse resp) {
		Throwable throwedException = t.getCause();
		for (ExceptionBind bind : binds) {
			if (bind.getExceptionClass().isInstance(throwedException)) {
				bind.getHandler(injector).handleException(throwedException, servlet, req, resp);
				return;
			}
		}		
		defaultHandler.handleException(throwedException, servlet, req, resp);
	}

}
