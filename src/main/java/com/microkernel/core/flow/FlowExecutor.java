package com.microkernel.core.flow;

import com.microkernel.core.service.Service;

public interface FlowExecutor {

	String executeService(Service state);
}
