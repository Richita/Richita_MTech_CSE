package com.microkernel.core.task.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.microkernel.core.Service;
import com.microkernel.core.flow.ServiceExecutor;

public class AsyncServiceExecutor implements ServiceExecutor,RejectedExecutionHandler {

	private ThreadPoolTaskExecutor task = new ThreadPoolTaskExecutor();
	
	
	
	public ThreadPoolTaskExecutor getTask() {
		return task;
	}

	public void setTask(ThreadPoolTaskExecutor task) {
		this.task = task;
	}

	public AsyncServiceExecutor() {
		
	}
	
	@Override
	public Future<String> executeService(final Service<? super Object> service,final Object request) {
		Future<String> submit = task.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {
				String result = service.process(request);
				return result;
			}
		});
		return submit;
	}

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		System.out.println(r);
	}

}
