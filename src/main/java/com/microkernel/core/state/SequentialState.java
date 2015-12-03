package com.microkernel.core.state;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microkernel.core.Service;
import com.microkernel.core.ServiceContext;
import com.microkernel.core.flow.ServiceExecutor;
import com.microkernel.core.flow.StateExecutionStatus;

/**
 * Sequential state will execute service one after other in sequence, failure of one service will stop the state
 * and the execution will move to failure state defined in process-definition.xml
 * 
 * @author NinadIngole
 *
 */
public class SequentialState extends AbstractState {

	private Logger log = LoggerFactory.getLogger(SequentialState.class);

	public SequentialState(String name, List<Service<?>> services, long timeout) {
		super(name, services);
		if (timeout > 0)
			this.setTimeout(timeout);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public StateExecutionStatus handle(ServiceExecutor executor, ServiceContext context) throws TimeoutException {
		StateExecutionStatus status = StateExecutionStatus.UNKNOWN;

		List<Service<?>> services = getServices();
		List<String> exitStatus = new ArrayList<String>();
		for (Service service : services) {
			Future<String> executeService = executor.executeService(service, context);
			try {
				String result = executeService.get(getTimeout(), TimeUnit.MILLISECONDS);
				if (result.equals("SUCCESS")) {
					exitStatus.add(result);
					status = StateExecutionStatus.COMPLETED;
				} else {
					status = StateExecutionStatus.FAIL;
					break;
				}

			} catch (InterruptedException e) {
				status = StateExecutionStatus.FAIL;
				log.error(e.getMessage(), e);
				e.printStackTrace();
			} catch (ExecutionException e) {
				status = StateExecutionStatus.FAIL;
				log.error(e.getMessage(), e);
				e.printStackTrace();
			}
		}
		log.info("State = "+getName()+" : ["+status.getName()+"]");
		return status;

	}

	/**
	 * Future extensibility not in use
	 */
	public boolean isEndState() {
		return false;
	}
}
