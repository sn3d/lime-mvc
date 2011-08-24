package org.zdevra.guice.mvc;

import java.lang.reflect.Method;

import com.google.inject.Injector;

class MappingData {

// ------------------------------------------------------------------------
	
	public HttpMethod requestType;
	public String      path;
	public String      resultName;
	public Injector    injector;
	public Class<?>    controllerClass;
	public Method      method;
	
// ------------------------------------------------------------------------
		
	public MappingData(Class<?> controllerClass, Method method, HttpMethod requestType, String path, String resultName, Injector injector) {
		this.controllerClass = controllerClass;
		this.method = method;
		this.path = path;
		this.resultName = resultName;
		this.requestType = requestType;
		this.injector = injector;
	}
	
// ------------------------------------------------------------------------
}
