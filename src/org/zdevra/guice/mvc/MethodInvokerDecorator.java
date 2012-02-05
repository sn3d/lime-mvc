package org.zdevra.guice.mvc;

/**
 * Method invoker's basic decorator. All decorators should extends 
 * this abstract class.
 * 
 * @author sn3d
 */
abstract class MethodInvokerDecorator implements MethodInvoker {
	
// ------------------------------------------------------------------------
	
	protected final MethodInvoker decoratedInvoker;

// ------------------------------------------------------------------------
	
	/**
	 * Constructor
	 * 
	 * @param decoratedInvoker
	 */
	protected MethodInvokerDecorator(MethodInvoker decoratedInvoker)
	{
		this.decoratedInvoker = decoratedInvoker;
	}
				
// ------------------------------------------------------------------------

}
