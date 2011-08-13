package org.zdevra.guice.mvc;

import java.lang.annotation.Annotation;

public interface ViewScanner {	
	public View scan(Annotation[] controllerAnotations);
}
