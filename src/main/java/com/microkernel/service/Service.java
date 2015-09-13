package com.microkernel.service;

public interface Service<T> {
	
	String getName();
	
	ServiceStatus execute(final T request,ServiceContext context);
	
}
