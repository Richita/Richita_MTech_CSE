package com.microkernel.service;

import com.microkernel.core.Context;

public interface ServiceContext extends Context<String, Object> {
	
	Object getInput(String key);
	
	void setOutput(String key,Object data);
	
	String getServiceName();

}
