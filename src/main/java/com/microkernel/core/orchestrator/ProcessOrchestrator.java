package com.microkernel.core.orchestrator;

import org.springframework.beans.factory.annotation.Autowired;

import com.microkernel.core.Orchestrator;
import com.microkernel.core.ProcessExecutor;
import com.microkernel.core.flow.Flow;
import com.microkernel.core.flow.FlowHolder;

/**
 * @author NinadIngole
 *
 */
public class ProcessOrchestrator implements Orchestrator {

	@Autowired
	private FlowHolder holder;
	
	private ProcessExecutor executor;
	
	
	public FlowHolder getHolder() {
		return holder;
	}

	public void setHolder(FlowHolder holder) {
		this.holder = holder;
	}

	
	public ProcessExecutor getExecutor() {
		return executor;
	}

	public void setExecutor(ProcessExecutor executor) {
		this.executor = executor;
	}

	@Override
	public FlowHolder getFlowHolder() {
		return holder;
	}

	@Override
	public Object process(Object request,String flowname) {
		
		Flow flow = this.holder.getFlow(flowname);
		
		executor.execute(request, flow);
		
		
		
//		request.g
		return null;
	}

}
