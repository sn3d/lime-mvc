package org.zdevra.guice.mvc.jsilver;

import org.zdevra.guice.mvc.MvcModule;

import com.google.clearsilver.jsilver.JSilver;
import com.google.inject.multibindings.Multibinder;

public abstract class JSilverModule extends MvcModule {

// ------------------------------------------------------------------------
		
	private Multibinder<ModelConvertor> modelConvertors;
	
// ------------------------------------------------------------------------
	
	protected abstract void configureControllers(JSilver jSilver) throws Exception;

// ------------------------------------------------------------------------

	@Override
	protected final void configureControllers() throws Exception {
		try {
			JSilver jSilver = 
				new JSilver( 
					new ServletContextResourceLoader(getServletContext()) 
				);
			
			bind(JSilver.class).toInstance(jSilver);
			bind(ModelService.class);
			
			registerViewScanner(JSilverScanner.class);		
			modelConvertors = Multibinder.newSetBinder(binder(), ModelConvertor.class);
			
			registerModelConvertor(ModelCollectionConvertor.class);
			registerModelConvertor(ModelMapConvertor.class);
			
			configureControllers(jSilver);
		
		} finally {
			modelConvertors = null;
		}
	}
	
	
	protected final void registerModelConvertor(Class<? extends ModelConvertor> modelConvClass) {
		modelConvertors.addBinding().to(modelConvClass);
	}
	
	
	protected final void registerModelConvertor(ModelConvertor modelConv) {
		modelConvertors.addBinding().toInstance(modelConv);
	}
	
	
	protected final void bindViewNameToJSilver(String viewName, String jsilverFile) {
		bindViewName(viewName).toViewInstance( new JSilverView(jsilverFile) );
	}
		
// ------------------------------------------------------------------------
}
