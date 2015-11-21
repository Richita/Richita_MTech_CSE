package com.microkernel.core.flow;

import java.util.Collection;

import com.microkernel.core.ServiceContext;

/**
 * 
 * A flow will contain all the state transitions to carry out the task.
 * There will be multiple flow and will be parsed from process-definition.
 * 
 * @author NinadIngole
 * @version 1.0
 */
public interface Flow {

	String getName();
	
	Collection<State> getStates();
	
	void start(ServiceContext context);
	
}
