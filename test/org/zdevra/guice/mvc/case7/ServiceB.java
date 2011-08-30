package org.zdevra.guice.mvc.case7;

import com.google.inject.ImplementedBy;

@ImplementedBy(ServiceBImpl.class)
public interface ServiceB {
	public void methodB();
}
