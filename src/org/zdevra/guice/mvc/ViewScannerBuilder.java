package org.zdevra.guice.mvc;

import com.google.inject.Binder;
import com.google.inject.multibindings.Multibinder;

/**
 * This builder class provide registering custom and default
 * scanners. Class is called and used by the {@link MvcModule}.
 * 
 * @see MvcModule
 */
class ViewScannerBuilder {
	
// ------------------------------------------------------------------------
	
	private final Multibinder<ViewScanner> scannersBinder;
	
// ------------------------------------------------------------------------
	
	/**
	 * Constructor
	 */
	public ViewScannerBuilder(Binder binder) {
		scannersBinder = Multibinder.newSetBinder(binder, ViewScanner.class);
	}
			
	public void as(Class<? extends ViewScanner> scannerClass) {
		scannersBinder.addBinding().to(scannerClass);
	}
	
	public void asInstance(ViewScanner scanner) {
		scannersBinder.addBinding().toInstance(scanner);
	}
	
// ------------------------------------------------------------------------

}
