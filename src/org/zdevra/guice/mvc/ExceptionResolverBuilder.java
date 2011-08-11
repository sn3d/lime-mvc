package org.zdevra.guice.mvc;

import org.zdevra.guice.mvc.MvcModule.ExceptionResolverBindingBuilder;

import com.google.inject.Binder;
import com.google.inject.multibindings.Multibinder;

/** 
 * The builder is responsible for building a bindings
 * between exceptions and exception handlers for
 * {@link GuiceExceptionResolver}
 * This builder is used internally by {@link MvcModule}.
 * 
 * @see MvcModule
 * @see GuiceExceptionResolver
 */
class ExceptionResolverBuilder {
	
	private final Multibinder<ExceptionBind> exceptionBinder; 
	private int orderIndex;
	
	
	public ExceptionResolverBuilder(Binder binder) {
		exceptionBinder = Multibinder.newSetBinder(binder, ExceptionBind.class);
		orderIndex = 0;
	}
	
	public ExceptionResolverBindingBuilder bindException(Class<? extends Throwable> exceptionClass) {
		return new ExceptionResolverBindBuilderImpl(exceptionClass);
	}
		
	public class ExceptionResolverBindBuilderImpl implements ExceptionResolverBindingBuilder {
		
		private final Class<? extends Throwable> exceptionClass;
		
		public ExceptionResolverBindBuilderImpl(Class<? extends Throwable> exceptionClass) {
			this.exceptionClass = exceptionClass;
		}

		@Override
		public void toHandler(Class<? extends ExceptionHandler> handlerClass) {
			exceptionBinder.addBinding().toInstance(
					ExceptionBind.toClass(handlerClass, exceptionClass, orderIndex));
			orderIndex++;
		}

		@Override
		public void toHandlerInstance(ExceptionHandler handler) {
			exceptionBinder.addBinding().toInstance(
					ExceptionBind.toInstance(handler, exceptionClass, orderIndex));
			orderIndex++;			
		};	
	}
}
