package com.microkernel.task.executor;

import java.io.Serializable;

import com.microkernel.task.TaskExecutor;
import com.microkernel.util.Assert;

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
