package com.microkernel.core.state;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.microkernel.core.Service;
import com.microkernel.core.ServiceContext;
import com.microkernel.core.flow.FlowExecutionStatus;
import com.microkernel.core.flow.ServiceExecutor;
import com.microkernel.core.flow.StateExecutionStatus;

public class SequentialState extends AbstractState {

	private final String SEQ_TYPE = "sequential";


	public SequentialState(String name, List<Service<?>> services) {
		super(name,services);

	}



	@Override
	public StateExecutionStatus handle(ServiceExecutor executor,ServiceContext context) {
    	StateExecutionStatus status = StateExecutionStatus.UNKNOWN;

		List<Service<?>> services = getServices();
		List<String> exitStatus = new ArrayList<String>();
		for(Service service : services){
			Future<String> executeService = executor.executeService(service,context);
			try {
				String result = executeService.get();
				if(null != result){
					exitStatus.add(result);
				}
                status = StateExecutionStatus.COMPLETED;

			} catch (InterruptedException e) {
            	status = StateExecutionStatus.FAILED;
            	// LOG Exceptions
				e.printStackTrace();
			} catch (ExecutionException e) {
            	status = StateExecutionStatus.FAILED;
            	// Log Exceptions
				e.printStackTrace();
			}
		}
		
		return status;

	}
	
	public boolean isEndState() {
		return false;
	}
}
