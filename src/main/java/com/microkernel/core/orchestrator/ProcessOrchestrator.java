package com.microkernel.core.orchestrator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.microkernel.core.CallBack;
import com.microkernel.core.Orchestrator;
import com.microkernel.core.ProcessExecutor;
import com.microkernel.core.flow.Flow;
import com.microkernel.core.flow.FlowHolder;

/**
 * Implementation of Orchestrator Interface client code will send the request to
 * orchestrator with the request obejct, flowName, and a callback on which
 * microkernel after processing will send the resposne or error if any exception
 * occurs
 * 
 * @author NinadIngole
 *
 */
public final class ProcessOrchestrator implements Orchestrator, InitializingBean {

	private Logger log = LoggerFactory.getLogger(ProcessOrchestrator.class);

	@Autowired
	private FlowHolder flowHolder;

	private ProcessExecutor executor;

	public ProcessExecutor getExecutor() {
		return executor;
	}

	public void setExecutor(ProcessExecutor executor) {
		this.executor = executor;
	}

	@Override
	public FlowHolder getFlowHolder() {
		return flowHolder;
	}

	public void setFlowHolder(FlowHolder flowHolder) {
		this.flowHolder = flowHolder;
	}

	@Override
	public void process(Object request, String flowname, CallBack callback) {
		log.info("Starting Flow Execution of " + flowname);
		log.debug("Input is : " + request.toString());

		// retrieve flow from flowHolder
		Flow flow = null;
		try {
			flow = this.flowHolder.getFlow(flowname);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage(), e);
			callback.onError(e);
		}
		// send flow into execution if the request are more than the queue
		// capacity
		// of the executor then the task will get rejected
		if(null != flow){
			executor.execute(request, flow, callback);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(getExecutor(), "Executor is Null Cannot Execute Flows.");
		Assert.notNull(getFlowHolder(), "Flow Holder is Null no flows to execute.");
	}

	/**
	 * Not yet implemented
	 */
	@Override
	public void processSync(Object request, String flowName) {

	}

}
