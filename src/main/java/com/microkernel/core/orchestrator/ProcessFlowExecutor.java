package com.microkernel.core.orchestrator;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.microkernel.core.CallBack;
import com.microkernel.core.ProcessExecutor;
import com.microkernel.core.ServiceContext;
import com.microkernel.core.flow.Flow;

public class ProcessFlowExecutor implements ProcessExecutor, ApplicationListener<ContextClosedEvent> {

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

		FutureTask<Void> task = new FutureTask<Void>(new Runnable() {

			@Override
			public void run() {
				flow.start(ctx);
			}
		}, null);

		getExecutor().execute(task);

		try {
			Void void1 = task.get();
			Object response = ctx.getResponse();

			if (null == response) {
				callback.onError(new NullPointerException("There was no Response recieved from any service"));
			}

			callback.onResponse(response);
		} catch (InterruptedException e) {
			callback.onError(e);
		} catch (ExecutionException e) {
			callback.onError(e);
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
