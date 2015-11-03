package com.microkernel.core.flow;

import java.util.concurrent.Future;

import com.microkernel.core.Service;

public interface ServiceExecutor {

	Future<?> executeService(Service<? super Object> service,Object request);
	
}
