package com.microkernel.core;

import com.microkernel.core.Service;

public interface ServiceLocator {

	Service<?> getService(String name);
	
	void putService(String beanName,Service<?> service);
	
}
