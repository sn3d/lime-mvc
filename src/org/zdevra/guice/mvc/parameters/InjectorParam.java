package org.zdevra.guice.mvc.parameters;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.inject.Inject;

import org.zdevra.guice.mvc.InvokeData;

import com.google.inject.Key;
import com.google.inject.name.Named;

/**
 * The parameter's processor pick up the instance from Guice and forward the instance
 * to the method's parameter. 
 * 
 * Method must be annotated with {@literal @}Inject annocation. The {@literal @}Named annotation
 * for the method's parameters is supported as well.
 * 
 */
public class InjectorParam implements ParamProcessor {
/*---------------------------- m. variables ----------------------------*/

	private final Key<?> key;

/*----------------------------------------------------------------------*/
	
	static class Factory implements ParamProcessorFactory {

		
		@Override
		public ParamProcessor buildParamProcessor(ParamMetadata metadata) {
			if (isInjectAnnotated(metadata.getMethod())) {
				Annotation namedAnnotation = metadata.getAnnotation(Named.class);
				if (namedAnnotation == null) {
					namedAnnotation = metadata.getAnnotation(javax.inject.Named.class);
				}				
				return new InjectorParam(metadata.getType(), namedAnnotation);
			}
			return null;
		}
		
		
		private boolean isInjectAnnotated(Method method) {
			Annotation inject = method.getAnnotation(Inject.class);
			if (inject != null) {
				return true;
			}
			return false;
		}
		
	}

/*----------------------------------------------------------------------*/
	
	/**
	 * Constructor
	 */
	private InjectorParam(Class<?> type, Annotation annotation) {		
		if (annotation == null) {
			this.key = Key.get(type);
		} else {
			this.key = Key.get(type, annotation);
		}
	}

	
	@Override
	public Object getValue(InvokeData data) {				
		Object instance = data.getInjector().getInstance(key);
		return instance;
	}
	
	

/*----------------------------------------------------------------------*/
}
