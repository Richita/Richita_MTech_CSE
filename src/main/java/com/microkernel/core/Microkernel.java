package com.microkernel.core;

public interface Microkernel{
	
	void start();
	
	void halt();
	
	Object process(Object request);

}
