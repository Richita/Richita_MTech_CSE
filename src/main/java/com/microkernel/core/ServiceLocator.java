package com.microkernel.core;

import com.microkernel.core.service.Service;

public interface ServiceLocator {

	Service<?> getService(String name);
	
	void putService(String beanName,Service<?> service);
	
}
