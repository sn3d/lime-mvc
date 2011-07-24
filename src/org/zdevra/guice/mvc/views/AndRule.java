package org.zdevra.guice.mvc.views;

import javax.servlet.http.HttpServletRequest;

/**
 * Router's rule representing AND operator between two rules.
 * The rule is usefull for composition of complex rules.
 */
public class AndRule implements ViewRouterRule {
	
// ------------------------------------------------------------------------

	private final ViewRouterRule ruleA;
	private final ViewRouterRule ruleB;
	
// ------------------------------------------------------------------------
	
	public AndRule(ViewRouterRule ruleA, ViewRouterRule ruleB) {
		this.ruleA = ruleA;
		this.ruleB = ruleB;
	}
	
// ------------------------------------------------------------------------

	@Override
	final public boolean check(HttpServletRequest request) {
		return ruleA.check(request) && ruleB.check(request);
	}
	
// ------------------------------------------------------------------------
}
