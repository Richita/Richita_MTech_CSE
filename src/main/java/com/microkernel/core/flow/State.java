package com.microkernel.core.flow;

public interface State {

	String getName();
	
	void handle();
	
	boolean isEnd();
}
