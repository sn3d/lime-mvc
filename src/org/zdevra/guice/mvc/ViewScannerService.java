package org.zdevra.guice.mvc;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * This service provide scanning of controller class or controller's method,
 * where is looking for view annotations, then constructs and returns a 
 * view instance for concrete controller's class or method.
 *
 * This service contains various scanners for various views. If you want
 * create your own view annotation, you will create own scanner as a 
 * implementation of {@link ViewScanner} interface and then you will
 * register it in {@link MvcModule}.
 * 
 * <p>How to register custom scanner into service
 * <pre class="prettyprint">
 * class CustomModule extends MvcModule {
 *    protected void configureControllers() {
 *       ...
 *       registerViewScanner().as(Custom1ViewScanner.class);
 *       registerViewScanner().asInstance(new Custom2ViewScanner());
 *       ...
 *    }
 * }
 * </pre>
 * 
 */
@Singleton
public class ViewScannerService {
	
	private Collection<ViewScanner> scanners;
	
	@Inject
	public ViewScannerService(Set<ViewScanner> scanners) {
		this.scanners = Collections.unmodifiableCollection(scanners); 
	}
	
	
	public View scan(Annotation[] controllerAnnotations) {
		for (ViewScanner scanner : scanners) {
			View view = scanner.scan(controllerAnnotations);
			if (view != null && view != View.NULL_VIEW) {
				return view;
			}
		}
		return View.NULL_VIEW;
	}
		
}
