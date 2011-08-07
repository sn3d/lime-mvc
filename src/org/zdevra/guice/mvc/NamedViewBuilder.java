package org.zdevra.guice.mvc;

import org.zdevra.guice.mvc.views.JspView;

import com.google.inject.Binder;
import com.google.inject.name.Names;

class NamedViewBuilder {
// ------------------------------------------------------------------------
	
	private final Binder binder;
	
// ------------------------------------------------------------------------
	
	/**
	 * Constructor
	 */
	public NamedViewBuilder(Binder binder) {
		this.binder = binder;
	}
	
	
	public MvcModule.NamedViewBindingBuilder bindViewName(String viewName) {
		return new NamedViewBindingBudilerImpl(viewName);
	}
	
// ------------------------------------------------------------------------
	
	class NamedViewBindingBudilerImpl implements MvcModule.NamedViewBindingBuilder {
		
		private final String viewName;
		
		public NamedViewBindingBudilerImpl(String viewName) {
			this.viewName = viewName;
		}

		@Override
		public void toView(Class<? extends View> viewClass) {			
			binder.bind(View.class)
				.annotatedWith(Names.named(viewName))
				.to(viewClass);
		}

		@Override
		public void toViewInstance(View view) {
			binder.bind(View.class)
			.annotatedWith(Names.named(viewName))
			.toInstance(view);			
		}

		@Override
		public void toJsp(String pathToJsp) {
			binder.bind(View.class)
			.annotatedWith(Names.named(viewName))
			.toInstance(new JspView(pathToJsp));						
		}
		
	}
	
// ------------------------------------------------------------------------

}
