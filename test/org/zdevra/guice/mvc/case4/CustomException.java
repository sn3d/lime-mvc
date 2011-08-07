package org.zdevra.guice.mvc.case4;

public class CustomException extends RuntimeException {

	public CustomException() {
		super("CustomException");
	}
	
	public CustomException(String msg) {
		super(msg + "CustomException ");
	}

	
}
