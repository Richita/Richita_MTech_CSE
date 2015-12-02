package com.microkernel.core.flow;

import java.util.List;
import java.util.concurrent.TimeoutException;

import com.microkernel.core.Service;
import com.microkernel.core.ServiceContext;

/**
 *
 */
public interface State {

	String getName();

	List<Service<?>> getServices();

	StateExecutionStatus handle(ServiceExecutor executor,ServiceContext context) throws TimeoutException;
	
	boolean isEndState();
	
	long getTimeout();
}
