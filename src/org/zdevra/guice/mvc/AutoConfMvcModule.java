package org.zdevra.guice.mvc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.google.inject.ImplementedBy;
import com.google.inject.Module;

public class AutoConfMvcModule extends MvcModule {
	
	private final String[] packageNames;
	private final Class<?> rootClass;
	
	public AutoConfMvcModule(Class<?> rootClass, String... packageName) {
		this.packageNames = packageName;
		this.rootClass = rootClass;
	}

	@Override
	protected final void configureControllers() {		
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
	
	
	private void examine(Class<?> clazz) {
		try {
			examineController(clazz);
			examineModule(clazz);
			examineAbstractionImplementedBy(clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private boolean examineController(Class<?> clazz) {
		Controller controllerAnot = clazz.getAnnotation(Controller.class);
		if (controllerAnot != null) {
			if (controllerAnot.path().length() > 0) {
				control(controllerAnot.path()).withController(clazz);
				return true;
			}
		}		
		return false;
	}
	
	
	private boolean examineModule(Class<?> clazz) throws SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		
		//check if it's Module
		if (Module.class.isAssignableFrom(clazz)) {
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
	
	
	private boolean examineAbstractionImplementedBy(Class<?> clazz) {
		ImplementedBy implAnot = clazz.getAnnotation(ImplementedBy.class);
		if (implAnot != null) {
			bind(clazz);
			return true;
		}		
		return false;
	}

}
