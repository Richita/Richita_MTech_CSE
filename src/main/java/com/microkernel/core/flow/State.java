package com.microkernel.core.flow;

import java.util.Set;

import com.microkernel.core.Service;

/**
 *
 */
public interface State {

	String getName();

	Set<Service> getServices();

	FlowExecutionStatus handle(FlowExecutor executor);
	
	boolean isEndState();
}
