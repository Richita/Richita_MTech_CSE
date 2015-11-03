package com.microkernel.core.flow;

import java.util.Collection;

/**
 * Holder holds all the flows and provide methods to retrive it when required by other
 * component
 * 
 * @author NinadIngole
 * @version 1.0
 */
public interface FlowHolder {
	
	Collection<Flow> getFlows();

	Flow getFlow(String name);
	
}
