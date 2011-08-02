package org.zdevra.guice.mvc.case4;

import org.zdevra.guice.mvc.case1.Case1Test;

public class Case4Main {

	public static void main(String[] args) {
		try {
			/*
			Case4Test t = new Case4Test();
			t.prepare();
			t.testDefaultHandler();
			t.testCustomHandler();
			t.testAdvancedCustomHandler();
			t.testAdvancedHandled();
			*/
			Case1Test t = new Case1Test();
			t.prepare();
			t.testSimpleRequest();
			t.testException();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
