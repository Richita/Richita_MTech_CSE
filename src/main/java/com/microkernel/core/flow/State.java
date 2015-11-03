package com.microkernel.core.flow;

import java.util.List;

import com.microkernel.core.Service;

/**
 *
 */
public interface State {

	String getName();

	List<Service<?>> getServices();

	void handle(Object request,ServiceExecutor executor);
	
	boolean isEndState();
}
