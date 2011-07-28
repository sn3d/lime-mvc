package org.zdevra.guice.mvc.case2;

import com.google.inject.Singleton;

@Singleton
public class Case2ControllerImpl implements Case2Controller {

	@Override
	public String getCar(String val) {
		return "ford-" + val;
	}

}
