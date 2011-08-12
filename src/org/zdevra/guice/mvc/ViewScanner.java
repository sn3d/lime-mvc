package org.zdevra.guice.mvc;

import java.lang.annotation.Annotation;

public interface ViewScanner {	
	public View scanController(Annotation[] controllerAnotations);
	public View scanMethod(Annotation[] methodAnotations);
}
