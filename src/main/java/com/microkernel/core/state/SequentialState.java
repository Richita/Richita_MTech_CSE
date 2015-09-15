package com.microkernel.core.state;

import com.microkernel.core.flow.FlowExecutionStatus;
import com.microkernel.core.flow.FlowExecutor;
import com.microkernel.core.service.Service;

import java.util.Set;

public class SequentialState extends AbstractState {

	private final String SEQ_TYPE = "sequential";


	public SequentialState(String name, Set<Service> services) {
		super(name,services);

	}



	@Override
	public FlowExecutionStatus handle(FlowExecutor executor) {
		Set<Service> services = getServices();

		for(Service service : services){
			executor.executeService(service);
		}

		return null;
	}

	@Override
	public boolean isEndState() {
		return false;
	}
}
