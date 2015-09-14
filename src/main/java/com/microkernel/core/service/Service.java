package com.microkernel.core.service;

public interface Service<T> {
	
	String getName();
	
	ServiceStatus execute(final T request,ServiceContext context);
	
}
