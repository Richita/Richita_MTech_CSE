package com.microkernel.core;

import com.microkernel.core.flow.Flow;

public interface ProcessExecutor {

	void execute(final Object request,final Flow flow,CallBack callback);
	
}
