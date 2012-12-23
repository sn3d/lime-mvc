package org.zdevra.guice.mvc.freemarker;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			FreemarkerTest t = new FreemarkerTest();
			t.testFreemarkerBasic();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
