package org.zdevra.guice.mvc;


/**
 * Decorator class which do interception before controller's method invokation.
 * 
 * @author sn3d
 */
class MethodInvokerInterceptors extends MethodInvokerDecorator {
	
// ------------------------------------------------------------------------
	
	private final InterceptorChain chain;
	
// ------------------------------------------------------------------------
		
	
	/**
	 * Hidden constructor. Object is creating via 'create()' factory method.
	 * @param decoratedInvoker
	 */
	public MethodInvokerInterceptors(MethodInvoker decoratedInvoker, InterceptorChain chain)
	{
		super(decoratedInvoker);
		this.chain = chain;
	}
	
// ------------------------------------------------------------------------
	
	@Override
	public ModelAndView invoke(InvokeData data) 
	{		
		//preprocess
		boolean res = chain.preHandle(data.getRequest(), data.getResponse());
		if (!res) {
			return null;
		}
		
		//controller processing
		ModelAndView mav = decoratedInvoker.invoke(data);
		
		//postprocess
		chain.postHandle(data.getRequest(), data.getResponse(), mav);
		
		return mav;
	}
	
// ------------------------------------------------------------------------

}
