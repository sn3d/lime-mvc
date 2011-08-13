package org.zdevra.guice.mvc.case5;

import java.lang.annotation.Annotation;

import org.zdevra.guice.mvc.TestView;
import org.zdevra.guice.mvc.Utils;
import org.zdevra.guice.mvc.View;
import org.zdevra.guice.mvc.ViewScanner;

public class TestViewScanner implements ViewScanner {

	@Override
	public View scan(Annotation[] annotations) {
		ToTestView anot = Utils.getAnnotation(ToTestView.class, annotations);
		if (anot != null) {
			return new TestView("9");
		}
		return View.NULL_VIEW;
	}

}
