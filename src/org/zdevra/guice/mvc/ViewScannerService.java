package org.zdevra.guice.mvc;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.google.inject.Inject;

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
 */
public class ViewScannerService {
	
	private Collection<ViewScanner> scanners;
	
	@Inject
	public ViewScannerService(Set<ViewScanner> scanners) {
		this.scanners = Collections.unmodifiableCollection(scanners); 
	}
	

}
