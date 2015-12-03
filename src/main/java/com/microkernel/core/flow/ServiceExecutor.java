package com.microkernel.core.flow;

import java.util.concurrent.Future;

import com.microkernel.core.Service;
import com.microkernel.core.ServiceContext;

/**
 * Interface which is used by States to execute the services
 * @author NinadIngole
 *
 */
public interface ServiceExecutor {

	@SuppressWarnings("rawtypes")
	Future executeService(Service<? super Object> service,ServiceContext context);
	
}
