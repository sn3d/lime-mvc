package org.zdevra.guice.mvc.jsilver;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			JSilverTest t = new JSilverTest();
			t.testJSilverView();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
