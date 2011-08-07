package org.zdevra.guice.mvc.case4;


public class Case4Main {

	public static void main(String[] args) {
		try {
			Case4Test t = new Case4Test();
			t.prepare();
			t.testDefaultHandler();
			t.testCustomHandler();
			t.testAdvancedCustomHandler();
			t.testAdvancedHandled();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
