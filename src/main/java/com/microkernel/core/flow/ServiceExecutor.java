package com.microkernel.core.flow;

import java.util.concurrent.Future;

import com.microkernel.core.Service;
import com.microkernel.core.ServiceContext;

public interface ServiceExecutor {

	Future executeService(Service<? super Object> service,ServiceContext context);
	
}
