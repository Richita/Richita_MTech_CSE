package com.microkernel.core.service;

import com.microkernel.core.ServiceContext;

public interface Service<T> {
	
	String getName();
	
	void execute(final T request,ServiceContext context);
	
}
