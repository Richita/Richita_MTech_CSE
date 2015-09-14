package com.microkernel.core.flow;

/**
 * Holder holds all the flows and provide methods to retrive it when required by other
 * component
 * 
 * @author NinadIngole
 * @version 1.0
 */
public interface FlowHolder {
	
	Flow getFlow(String name);

}
