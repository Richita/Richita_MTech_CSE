package com.microkernel.core;

import com.microkernel.core.flow.FlowHolder;

public interface Orchestrator {

	
	FlowHolder getFlowHolder();
	
	void process(Object request,String flowName,CallBack callback);

	void processSync(Object request,String flowName);
}
