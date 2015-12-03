package com.microkernel.core.task.executor;

import java.io.Serializable;

import com.microkernel.core.task.TaskExecutor;
import com.microkernel.core.util.Assert;

/**
 * Async solves both problem. THis can go in future development
 * @author NinadIngole
 *
 */
public class SyncTaskExecutor implements TaskExecutor,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void execute(Runnable runnable) {
		Assert.notNull(runnable,"Runable cannot be null");
		runnable.run();
	}

}
