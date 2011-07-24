package org.zdevra.guice.mvc.views;

import javax.servlet.http.HttpServletRequest;

/**
 * Each view in the router is associated to some 
 * rule and router decides upon routing by calling 
 * the check method. When check method returns true, 
 * then router send request to the associated view.
 *  
 */
public interface ViewRouterRule {
	
	/**
	 * This method is called 
	 * @param request
	 * @return When method returns true, router then route request to associated view.
	 */
	public boolean check(HttpServletRequest request);
}
