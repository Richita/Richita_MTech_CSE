package com.microkernel.flow;

public interface State {

	String getName();
	
	void handle();
	
	boolean isEnd();
}
