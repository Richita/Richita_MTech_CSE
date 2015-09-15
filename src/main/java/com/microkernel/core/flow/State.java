package com.microkernel.core.flow;

import com.microkernel.core.service.Service;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;

import java.util.Set;

/**
 *
 */
public interface State {

	String getName();

	Set<Service> getServices();

	
	FlowExecutionStatus handle(FlowExecutor executor);
	
	boolean isEndState();
}
