package com.microkernel.core.task.executor;

import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.microkernel.core.Service;
import com.microkernel.core.flow.ServiceExecutor;

public class AsyncServiceExecutor implements ServiceExecutor,RejectedExecutionHandler {

	private ThreadPoolTaskExecutor task = new ThreadPoolTaskExecutor();
	
	
	public AsyncServiceExecutor() {
		task.setCorePoolSize(Runtime.getRuntime().availableProcessors()+1);
		task.setKeepAliveSeconds(40000);
		task.setMaxPoolSize(20);
		task.setThreadFactory(new ThreadFactory() {
			
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread("Service Thread");
				return t;
			}
		});
		task.setRejectedExecutionHandler(this);
		task.afterPropertiesSet();
	}
	
	@Override
	public Future<?> executeService(final Service<? super Object> service,final Object request) {
		service.process(request);
		Future<?> submit = task.submit(new Runnable() {
			
			@Override
			public void run() {
				service.process(request);
			}
		});
		return submit;
	}

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		System.out.println(r);
	}

}
