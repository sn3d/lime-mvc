/*****************************************************************************
 * Copyright 2011 Zdenko Vrabel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 *****************************************************************************/
package org.zdevra.guice.mvc.jsilver;

import javax.servlet.ServletContext;

import org.zdevra.guice.mvc.ViewModule;

import com.google.clearsilver.jsilver.JSilver;
import com.google.clearsilver.jsilver.resourceloader.ClassResourceLoader;
import com.google.inject.multibindings.Multibinder;

/**
 * The module adds the JSilver template engine support
 * into Lime MVC.
 * 
 * <h3>Usage</h3>
 * When you want to use JSilver template engine for
 * view rendering, you will install this JSilverModule
 * in your MvcModule. After that you may use {@ link ToJSilverView}
 * and JSilverView.
 * 
 * <p><br>
 * <b>example:</b>
 * <pre class="prettyprint">
 * public class MyModule extends MvcModule {
 *   protected void configureControllers(VelocityEngine velocity) {
 *      //setup controllers
 *      control("/*")...
 *      ...
 *      //setup views
 *      install(new JSilverModule(getServletContext());
 *      bindViewName("viewName").toViewInstance(new JSilverView("output.jsilver")); 
 *   }
 * }
 * </pre> 
 * 
 * <h3>Model conversion</h3>
 * The JSilver uses his own representation of data. For
 * this reason, the lime MVC have to converts his data to
 * JSilver's data. This is provided by Model Service and 
 * registered converters. If you want put your own object
 * into model, you will create converter by extending the 
 * {@link ModelObjectConverter} and you will register the
 * new converter calling the registerModelConvertor() method. 
 * <p><br>
 * <b>custom class:</b>
 * <pre class="prettyprint">
 * class Book {
 *   public final String name;
 *   public final String author;
	
 *   public Book(String name, String author) {
 *     this.name = name;
 *     this.author = author;
 *   }
 * }
 * </pre>
 * <br>
 * <b>implementation of converter:</b>
 * <pre class="prettyprint"> 
 * public class BookConverter extends ModelObjectConverter{@literal <}Book{@literal >} {
 *    public BookConverter() {
 *       super(Book.class)
 *    }
 *    
 *    public void convertObject(Book book, Data data, ModelService convService) {
 *       data.setValue("name",   obj.name);
 *       data.setValue("author", obj.author);
 *    }
 * }
 * </pre>
 * <br>
 * <b>registration of converter:</b>
 * <pre class="prettyprint">
 * public class MyModule extends JSilverModule {
 *    protected void configureControllers(JSilver jSilver) throws Exception {
 *       ...
 *       registerModelConvertor(BookConverter.class);
 *    }
 * }
 * </pre>
 */
public class JSilverModule extends ViewModule {

// ------------------------------------------------------------------------
		
	private final ServletContext context;
	private Multibinder<ModelConverter> modelConvertors;
	
// ------------------------------------------------------------------------
	
	/**
	 * Constructor for JSilver which loads files from WAR
	 */
	public JSilverModule(ServletContext context) {
		this.context = context;
	}
	
// ------------------------------------------------------------------------
	
	/**
	 * You will put your MVC configuration and convertor's registration 
	 * into this method.
	 * 
	 * @param jSilver
	 * @throws Exception
	 */
	protected void configureJSilver(JSilver jSilver) {
		
	}

// ------------------------------------------------------------------------

	@Override
	protected final void configureViews()
	{
		try {
			JSilver jSilver = null;
			
			if (context != null) {
				jSilver = new JSilver( 
					new ServletContextResourceLoader(context) 
				);
			} else {				
				jSilver = new JSilver( 
					new	ClassResourceLoader(JSilverModule.class) 
				);				
			}
			
			bind(JSilver.class).toInstance(jSilver);
			bind(ModelService.class);
			
			registerViewScanner(JSilverScanner.class);		
			modelConvertors = Multibinder.newSetBinder(binder(), ModelConverter.class);
			
			registerModelConvertor(ModelCollectionConverter.class);
			registerModelConvertor(ModelMapConverter.class);
			
			configureJSilver(jSilver);		
		} finally {
			modelConvertors = null;
		}
	}
	
	
	/**
	 * Method registers the concrete {@link ModelConverter}. Model converters
	 * are used for transforming Lime data model to JSIlver data model.
	 * @param modelConvClass
	 */
	protected final void registerModelConvertor(Class<? extends ModelConverter> modelConvClass) {
		modelConvertors.addBinding().to(modelConvClass);
	}
	

	/**
	 * Method registers the concrete instance of the {@link ModelConverter}. 
	 * Model converters are used for transforming Lime data model to JSIlver 
	 * data model.
	 * 
	 * @param modelConvClass
	 */
	protected final void registerModelConvertor(ModelConverter modelConv) {
		modelConvertors.addBinding().toInstance(modelConv);
	}
		
// ------------------------------------------------------------------------
}
