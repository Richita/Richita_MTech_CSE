package com.microkernel.core.task.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.microkernel.core.Service;
import com.microkernel.core.ServiceContext;
import com.microkernel.core.flow.ServiceExecutor;

/**
 * This ServiceExecutor is a Async which is used by both SequentialState and Parallel State,
 * It also has a threadpool which is diferent than the processExecutor ThreadPool. Making two different
 * thread pool have advantage of processing faster and on independent pool one can easily debug the code
 * to check which threadpool is getting exhausted in high TPS so that it can be tuned to optimal level 
 * for serving all the request.
 * 
 * @author NinadIngole
 *
 */
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
	
	@SuppressWarnings("rawtypes")
	@Override
	public Future executeService(final Service<? super Object> service,final ServiceContext context) {
		// Call service's process method passing request and context
		Future<String> future = task.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {
				String status = "FAIL";
				try{
					service.process(context.getRequest(), context);
					status = "SUCCESS";
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
		log.info("TASK REJECTED "+r.toString());
		log.debug("POOL details" + executor.toString());
	}

	/**
	 * Gracefully shutdown the threadpool when application stops.
	 */
	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		this.task.shutdown();
	}

}
