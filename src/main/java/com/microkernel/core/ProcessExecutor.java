package com.microkernel.core;

import com.microkernel.core.flow.Flow;

public interface ProcessExecutor {

	int SUCCESS = 1;
	int FAILED = -1;
	
	void execute(final Object request,final Flow flow,CallBack callback);
	
}
