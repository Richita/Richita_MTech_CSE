package com.microkernel.core.flow;

import java.util.List;
import java.util.concurrent.TimeoutException;

import com.microkernel.core.Service;
import com.microkernel.core.ServiceContext;

/**
 * Every State in process def is converted into object of State implementation by Process definition parser
 * with the details required to form a valid state for state machine
 *
 */
public interface State {

	String getName();

	List<Service<?>> getServices();

	StateExecutionStatus handle(ServiceExecutor executor,ServiceContext context) throws TimeoutException;
	
	boolean isEndState();
	
	long getTimeout();
}
