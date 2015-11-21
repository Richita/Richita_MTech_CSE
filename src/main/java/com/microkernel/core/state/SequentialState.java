package com.microkernel.core.state;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.microkernel.core.Service;
import com.microkernel.core.ServiceContext;
import com.microkernel.core.flow.FlowExecutionStatus;
import com.microkernel.core.flow.ServiceExecutor;

public class SequentialState extends AbstractState {

	private final String SEQ_TYPE = "sequential";


	public SequentialState(String name, List<Service<?>> services) {
		super(name,services);

	}



	@Override
	public void handle(ServiceExecutor executor,ServiceContext context) {
		List<Service<?>> services = getServices();
		List<String> exitStatus = new ArrayList<String>();
		for(Service service : services){
			Future<String> executeService = executor.executeService(service,context);
			try {
				String result = executeService.get();
				if(null != result){
					exitStatus.add(result);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}

	}
	
	public boolean isEndState() {
		return false;
	}
}
