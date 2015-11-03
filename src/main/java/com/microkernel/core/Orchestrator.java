package com.microkernel.core;

import com.microkernel.core.flow.FlowHolder;

public interface Orchestrator {

	
	FlowHolder getFlowHolder();
	
	Object process(Object request,String flowName);
	
}
