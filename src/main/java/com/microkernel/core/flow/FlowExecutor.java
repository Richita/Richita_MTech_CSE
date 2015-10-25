package com.microkernel.core.flow;

import com.microkernel.core.Service;

public interface FlowExecutor {

	String executeService(Service state);
}
