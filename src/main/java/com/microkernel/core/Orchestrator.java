package com.microkernel.core;

import com.microkernel.core.flow.FlowHolder;

/**
 * Orchestrator is heart of Microkernel every request in microkernel lands in orchestrator
 * It knows how to process the request and all the details required to send back the response
 * @author NinadIngole
 *
 */
public interface Orchestrator {

	
	FlowHolder getFlowHolder();
	
	void process(Object request,String flowName,CallBack callback);

	void processSync(Object request,String flowName);
}
