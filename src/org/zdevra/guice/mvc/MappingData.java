package org.zdevra.guice.mvc;

import java.lang.reflect.Method;

import com.google.inject.Injector;

class MappingData {

// ------------------------------------------------------------------------
	
	public HttpMethodType httpMethodType;
	public String         path;
	public String         resultName;
	public Injector       injector;
	public Class<?>       controllerClass;
	public Method         method;
	
// ------------------------------------------------------------------------
		
	public MappingData(Class<?> controllerClass, Method method, HttpMethodType httpMethodType, String path, String resultName, Injector injector) {
		this.controllerClass = controllerClass;
		this.method = method;
		this.path = path;
		this.resultName = resultName;
		this.httpMethodType = httpMethodType;
		this.injector = injector;
	}
	
// ------------------------------------------------------------------------
}
