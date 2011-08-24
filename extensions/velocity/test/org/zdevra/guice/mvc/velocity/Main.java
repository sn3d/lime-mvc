package org.zdevra.guice.mvc.velocity;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			TestVelocity t = new TestVelocity();
			t.testView();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
