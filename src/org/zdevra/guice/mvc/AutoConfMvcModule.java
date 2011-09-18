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
package org.zdevra.guice.mvc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.google.inject.ImplementedBy;
import com.google.inject.Module;
import org.zdevra.guice.mvc.exceptions.AutoConfException;

/**
 * The module goes through concrete package, examine each class
 * and automatically install modules, register converters or bind controllers.
 */
public class AutoConfMvcModule extends MvcModule {

// ------------------------------------------------------------------------
	
	private final String[] packageNames;
	private final Class<?> rootClass;

// ------------------------------------------------------------------------

    /**
     * Constructor needs some class object in package and package name.
     * Why needs a class object? Because we can have different class loaders.
     *
     * @param rootClass
     * @param packageName
     */
	public AutoConfMvcModule(Class<?> rootClass, String... packageName) {
		this.packageNames = packageName;
		this.rootClass = rootClass;
	}


// ------------------------------------------------------------------------


	@Override
	protected final void configureControllers()
    {
		PackageExaminer examiner = 
			new PackageExaminer(new ClassExamineFunctor() {
				@Override
				public void clazz(Class<?> clazz) {
					examine(clazz);
				}				
			});
		
		for (int i = 0; i < packageNames.length; ++i) {
			examiner.examinePackage(rootClass, packageNames[i]);
		}
	}	
	
	
	private void examine(Class<?> clazz)
    {
		try {
			examineController(clazz);
			examineModule(clazz);
			examineAbstractionImplementedBy(clazz);
            examineConverter(clazz);
		} catch (Exception e) {
			throw new AutoConfException(clazz, e);
		}
	}
	
	
	private boolean examineController(Class<?> clazz)
    {
		Controller controllerAnot = clazz.getAnnotation(Controller.class);
		if (controllerAnot != null) {
			if (controllerAnot.path().length() > 0) {
				control(controllerAnot.path()).withController(clazz);
				return true;
			}
		}		
		return false;
	}
	
	
	private boolean examineModule(Class<?> clazz)
        throws SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
		//check if it's Module
		if (Module.class.isAssignableFrom(clazz))
        {
			if (!MvcModule.class.isAssignableFrom(clazz)) {
				Constructor<?> constructor = clazz.getConstructor(new Class<?>[]{});
				if (constructor != null) {
					Module module = (Module)constructor.newInstance(new Object[]{});
					install(module);
					return true;
				}
			}
		}
		
		return false;
	}
	
	
	private boolean examineAbstractionImplementedBy(Class<?> clazz)
    {
		ImplementedBy implAnot = clazz.getAnnotation(ImplementedBy.class);
		if (implAnot != null) {
			bind(clazz);
			return true;
		}		
		return false;
	}


    private boolean examineConverter(Class<?> clazz)
        throws NoSuchMethodException
    {
        if (ConversionService.ConverterFactory.class.isAssignableFrom(clazz)) {
            Class<ConversionService.ConverterFactory> factoryClazz = (Class<ConversionService.ConverterFactory>) clazz;
            registerConverter(factoryClazz);
            return true;
        }
        return false;
    }

// ------------------------------------------------------------------------
}
