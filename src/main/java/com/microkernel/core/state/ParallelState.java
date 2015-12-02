package com.microkernel.core.state;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.slf4j.Logger;

import com.microkernel.core.Service;
import com.microkernel.core.ServiceContext;
import com.microkernel.core.flow.ServiceExecutor;
import com.microkernel.core.flow.StateExecutionStatus;

/**
 * This class provides functionality to process services in prallel mode in
 * parallel mode the service executed on individual thread. [WARNING] do not
 * execute services in parallel that have dependencies as in input or something
 * else on each other, use sequential state in that case.
 * 
 * Created by NinadIngole on 9/14/2015.
 */
public class ParallelState extends AbstractState {

	private Logger log = LoggerFactory.getLogger(ParallelState.class);

	public ParallelState(String name, List<Service<?>> services, long timeout) {
		super(name, services);
		if (timeout > 0)
			this.setTimeout(timeout);
	}

	/**
	 * This method is invoked by the service executor to process the services
	 * @throws TimeoutException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public StateExecutionStatus handle(final ServiceExecutor executor, final ServiceContext context) throws TimeoutException {
		log.info("inside Parallel State handle()");
		Assert.notNull(executor, "Service Executor Passed is NULL.");
		Assert.notNull(context, "Service Context Passed is NULL.");

		StateExecutionStatus status = StateExecutionStatus.UNKNOWN;
		Collection<Future<String>> tasks = new ArrayList<Future<String>>();
		List<Service<?>> services = getServices();

		for (final Service service : services) {
			Future<String> future = executor.executeService(service, context);
			tasks.add(future);

		}
		
		for (Future<String> task : tasks) {
			try {
				String result = task.get(getTimeout(), TimeUnit.MILLISECONDS);
				if(result.equals("DONE")){
					status = StateExecutionStatus.COMPLETED;
				}else{
					status = StateExecutionStatus.FAILED;
				}
			} catch (ExecutionException e) {
				status = StateExecutionStatus.FAILED;
				log.error(e.getMessage(), e);
			} catch (InterruptedException e) {
				status = StateExecutionStatus.FAILED;
				log.error(e.getMessage(), e);
			}
		}
		log.info("State = "+this.getName()+" : ["+status.getName()+"]");
		return status;

	}

	/**
	 * Future Extensibility to add batch support
	 */
	public boolean isEndState() {
		return false;
	}

	
	
	
}
