package com.microkernel.core.flow.support;

import java.util.Collection;
import java.util.HashMap;

import com.microkernel.core.flow.Flow;
import com.microkernel.core.flow.FlowHolder;

public class FlowHolderImpl implements FlowHolder {

	
	
	private HashMap<String, Flow> flows = new HashMap<String, Flow>();
	
	public FlowHolderImpl(Collection<Flow> flows) {
		for(Flow flow: flows){
			this.flows.put(flow.getName(), flow);
		}
	}
	
	public Flow getFlow(String name) {
		return this.flows.get(name);
	}


	public Collection<Flow> getFlows() {
		return this.flows.values();
	}
}
