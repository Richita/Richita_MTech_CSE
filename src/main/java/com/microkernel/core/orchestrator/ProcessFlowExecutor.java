package com.microkernel.core.orchestrator;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
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

		FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
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
		int returnType = 0;
		try {
			returnType = task.get();
		} catch (Exception e) {
			returnType = FAILED;
		}
		switch (returnType) {
		case SUCCESS:
			Object response = ctx.getResponse();

			if (null == response) {
				callback.onError(new NullPointerException("There was no Response recieved from any service"));
			}

			callback.onResponse(response);
			break;

		case FAILED:
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

	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		this.getExecutor().shutdown();
	}

}
