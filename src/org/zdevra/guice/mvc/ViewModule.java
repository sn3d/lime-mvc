package org.zdevra.guice.mvc;

import org.zdevra.guice.mvc.MvcModule.NamedViewBindingBuilder;

import com.google.inject.AbstractModule;

/**
 * The module provide a view configuration apart of the main MvcModule.
 * 
 * This is usefull when you will implement your own view extensions or
 * if you want to change view name bindings for testing purpose.  
 *  
 */
public abstract class ViewModule extends AbstractModule {
	
// ------------------------------------------------------------------------
	
	private ViewScannerBuilder viewScannerBuilder;
	private NamedViewBuilder   namedViewBudiler;
	
// ------------------------------------------------------------------------

	/**
	 * In this method you will setup your views
	 */
	protected abstract void configureViews();
	
// ------------------------------------------------------------------------

	@Override
	protected final void configure() {	
		viewScannerBuilder = new ViewScannerBuilder(binder());
		namedViewBudiler = new NamedViewBuilder(binder());
		try {			
			requireBinding(ViewResolver.class);
			requireBinding(ViewScannerService.class);
			configureViews();
		} finally {
			viewScannerBuilder = null;
			namedViewBudiler = null;
		}
	}
	
// ------------------------------------------------------------------------
	
	/**
	 * The method register into {@link ViewScannerService} a custom
	 * view scanner as a class
	 * 
	 * @see ViewScannerService
	 */
	protected final void registerViewScanner(Class<? extends ViewScanner> scannerClass) {
		this.viewScannerBuilder.as(scannerClass);
	}

	
	/**
	 * The method register into {@link ViewScannerService} a custom
	 * view scanner as a instance.
	 * 
	 * @see ViewScannerService
	 */
	protected final void registerViewScanner(ViewScanner scannerInstance) {
		this.viewScannerBuilder.asInstance(scannerInstance);
	}

	
	/**
	 * Method bind to view's name some view.
	 */
	protected final NamedViewBindingBuilder bindViewName(String viewName) {
		return this.namedViewBudiler.bindViewName(viewName);
	}
	
// ------------------------------------------------------------------------

}
