package com.microkernel.core.orchestrator;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.microkernel.core.CallBack;
import com.microkernel.core.ProcessExecutor;
import com.microkernel.core.ServiceContext;
import com.microkernel.core.flow.Flow;
import com.microkernel.core.flow.StateExecutionStatus;

/**
 * ProcessFlowExecutor will execute the flow passed by the Orchestrator, the
 * threadpool of processflowexecutor is configurable through property file the
 * thread pool capacity or the numberof threads should be optimal to serve all
 * request. The formula
 * 
 * can be used to calculate the optimal thread value
 * 
 * @author NinadIngole
 *
 */
public class ProcessFlowExecutor implements ProcessExecutor, ApplicationListener<ContextClosedEvent> {

	private Logger log = LoggerFactory.getLogger(ProcessFlowExecutor.class);

	private final String STATUS_KEY = "status";

	private ThreadPoolTaskExecutor executor;
	private ThreadLocal<ServiceContext> contexts = new ThreadLocal<ServiceContext>() {
		@Override
		protected ServiceContext initialValue() {
			return new ServiceContext();
		}
	};

	@Override
	public void execute(final Object request, final Flow flow, CallBack callback) {
		final ServiceContext ctx = contexts.get();
		ctx.clear();
		ctx.setRequest(request);

		FutureTask<StateExecutionStatus> task = new FutureTask<StateExecutionStatus>(new Callable<StateExecutionStatus>() {

			@Override
			public StateExecutionStatus call() throws Exception {
				flow.start(ctx);
				return ctx.get(STATUS_KEY);
			}
		});
		getExecutor().execute(task);

		/**
		 * this seems to wait inifinite but its not as if any service gets
		 * timeout then it will be be propogated back to SimpleFlow which will
		 * stop the entire flow
		 */
		StateExecutionStatus returnType = StateExecutionStatus.UNKNOWN;
		try {
			returnType = task.get();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			returnType = StateExecutionStatus.FAIL;
		}
		switch (returnType.getName()) {
			// INCASE of sucess give event callback on onResponse with resposne object
			case "SUCCESS":
				Object response = ctx.getResponse();
				
				// response object cannot be null.
				if (null == response) {
					callback.onError(new NullPointerException("There was no Response recieved as flow result. Is this a batch process ?"));
				}
	
				callback.onResponse(response);
				break;

			// INCASE of Failure give event callback on onError method with the exception details
			case "FAIL":
				Throwable exception = ctx.getException();
				if (exception == null)
					exception = new Throwable("Something happened at Service Level that microkernel is not aware of.");
				callback.onError(exception);
				break;
		}

	}

	public ThreadPoolTaskExecutor getExecutor() {
		return executor;
	}

	public void setExecutor(ThreadPoolTaskExecutor executor) {
		this.executor = executor;
	}

	/**
	 * Gracefully shutdown the threadpool when spring application context is destroyed i.e. Application is stopped
	 */
	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		this.getExecutor().shutdown();
	}

}
