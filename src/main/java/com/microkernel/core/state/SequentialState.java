package com.microkernel.core.state;

import com.microkernel.core.flow.FlowExecutor;
import com.microkernel.core.service.Service;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;

import java.util.Set;

public class SequentialState extends AbstractState {

	private final String SEQ_TYPE = "sequential";

	private final Set<Service> services;
	
	public SequentialState(String name, Set<Service> services) {
		super(name);
		this.services = services;
	}



	@Override
	public FlowExecutionStatus handle(FlowExecutor executor) {
		for(Service service : services){
			executor.executeService(service);
		}

		return FlowExecutionStatus.COMPLETED;
	}

	@Override
	public boolean isEndState() {
		return false;
	}
}
