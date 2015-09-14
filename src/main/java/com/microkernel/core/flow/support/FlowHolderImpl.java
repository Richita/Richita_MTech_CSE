
package com.microkernel.core.flow.support;

import java.util.Collection;
import java.util.HashMap;

import com.microkernel.core.flow.Flow;
import com.microkernel.core.flow.FlowHolder;

public class FlowHolderImpl implements FlowHolder {

	private static FlowHolderImpl INSTANCE = null;
	
	private HashMap<String, Flow> flows = new HashMap<String, Flow>();
	
	public static FlowHolderImpl getInstance(){
		if(INSTANCE == null){
			synchronized (FlowHolderImpl.class) {
				if(INSTANCE == null){
					INSTANCE = new FlowHolderImpl();
				}
			}
		}
		return INSTANCE;
	}
	
	public Flow getFlow(String name) {
		return this.flows.get(name);
	}
	
	public void setFlows(Collection<Flow> flows){
		for(Flow flow: flows){
			this.flows.put(flow.getName(), flow);
		}
	}

}
