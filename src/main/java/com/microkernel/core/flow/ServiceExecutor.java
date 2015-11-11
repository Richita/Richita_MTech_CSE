package com.microkernel.core.flow;

import java.util.concurrent.Future;

import com.microkernel.core.Service;

public interface ServiceExecutor {

	Future<String> executeService(Service<? super Object> service,Object request);
	
}
