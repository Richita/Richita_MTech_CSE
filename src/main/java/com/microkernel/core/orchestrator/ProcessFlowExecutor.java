package com.microkernel.core.orchestrator;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.springframework.context.Lifecycle;

import com.microkernel.core.ProcessExecutor;
import com.microkernel.core.flow.Flow;
public class ProcessFlowExecutor implements ProcessExecutor,Lifecycle{

	ExecutorService executor = Executors.newFixedThreadPool(8);
	
	@Override
	public void execute(final Object request, final Flow flow) {
		FutureTask<Void> task = new FutureTask<Void>(new Runnable() {
			
			@Override
			public void run() {
				flow.start(request);
			}
		},null);
		
		
		executor.execute(task);
		
		try {
			task.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} 
		
	}

	@Override
	public boolean isRunning() {
		return true; // TODO for time being
	}

	@Override
	public void start() {
		
	}

	@Override
	public void stop() {
		this.executor.shutdown();
	}

}
