package com.microkernel.core;

import com.microkernel.core.Service;

/**
 * Service Locator implementation locates all the service implementation bean and will store instances of all
 * this will be then used by the service parser to connect service instance into the flow
 * @author NinadIngole
 *
 */
public interface ServiceLocator {

	Service<?> getService(String name);
	
	void putService(String beanName,Service<?> service);
	
}
