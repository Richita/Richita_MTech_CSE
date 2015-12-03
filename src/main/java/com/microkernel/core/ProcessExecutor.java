package com.microkernel.core;

import com.microkernel.core.flow.Flow;

/**
 * Interface which is used by orchestrator to process flow
 * @author NinadIngole
 *
 */
public interface ProcessExecutor {

	
	void execute(final Object request,final Flow flow,CallBack callback);
	
}
