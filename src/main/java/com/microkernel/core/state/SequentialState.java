package com.microkernel.core.state;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.microkernel.core.Service;
import com.microkernel.core.flow.FlowExecutionStatus;
import com.microkernel.core.flow.ServiceExecutor;

public class SequentialState extends AbstractState {

	private final String SEQ_TYPE = "sequential";


	public SequentialState(String name, List<Service<?>> services) {
		super(name,services);

	}



	@Override
	public void handle(Object request,ServiceExecutor executor) {
		List<Service<?>> services = getServices();

		for(Service service : services){
			Future<?> executeService = executor.executeService(service,request);
			/*try {
				Object object = executeService.get();
				if(object != null)
					break;
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}*/
		}

	}
	
	public boolean isEndState() {
		return false;
	}
}
