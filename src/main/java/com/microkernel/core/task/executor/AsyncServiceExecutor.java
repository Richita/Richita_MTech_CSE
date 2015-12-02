package com.microkernel.core.task.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.Lifecycle;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.microkernel.core.Service;
import com.microkernel.core.ServiceContext;
import com.microkernel.core.flow.ServiceExecutor;

public class AsyncServiceExecutor implements ServiceExecutor,RejectedExecutionHandler,ApplicationListener<ContextClosedEvent> {

	private Logger log = LoggerFactory.getLogger(AsyncServiceExecutor.class);
	
	private ThreadPoolTaskExecutor task;
	
	
	public ThreadPoolTaskExecutor getTask() {
		return task;
	}

	public void setTask(ThreadPoolTaskExecutor task) {
		this.task = task;
	}

	public AsyncServiceExecutor() {
		
	}
	
	@Override
	public Future executeService(final Service<? super Object> service,final ServiceContext context) {
		Future<String> future = task.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {
				String status = "FAIL";
				try{
					service.process(context.getRequest(), context);
					status = "DONE";
				}catch(Exception e){
					context.setException(e);
				}
				log.info("Service : "+service.getClass().getSimpleName()+" ["+status+"]");
				return status;
			}
		});
		
		return future;
	}

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		System.out.println(r);
	}

	

	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		this.task.shutdown();
	}

}
