package org.zdevra.guice.mvc.views;

import javax.servlet.http.HttpServletRequest;

/**
 * Implemetation of rule which make decision based 
 * upon device's type extracted from User-Agent. There are defined rules
 * for few devices as well(iPhone, iPad, Android).
 */
public class DeviceRule implements ViewRouterRule {
	
// ------------------------------------------------------------------------
	
	private final String deviceId;

// ------------------------------------------------------------------------

	public static final DeviceRule IPHONE_DEVICE  = new DeviceRule("iPhone");
	public static final DeviceRule IPAD_DEVICE    = new DeviceRule("iPad");
	public static final DeviceRule ANDROID_DEVICE = new DeviceRule("Android");

// ------------------------------------------------------------------------
				
	public DeviceRule(String deviceId) {
		this.deviceId = deviceId;
	}

// ------------------------------------------------------------------------
	
	@Override
	final public boolean check(HttpServletRequest request) {		
		String userAgent = request.getHeader("User-Agent");
		if (userAgent.contains(deviceId)) {
			return true;
		}
		return false;
	}

// ------------------------------------------------------------------------

}
