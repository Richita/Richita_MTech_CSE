package com.microkernel.core;

/**
 * This is the interface that will be implemented by all the services written
 * by developer there.
 * 
 * @author NinadIngole
 * @version 0.1
 */
public interface Service<T> {

	String getName();
	
	void process(T data, ServiceContext context)throws Exception;
	
}
