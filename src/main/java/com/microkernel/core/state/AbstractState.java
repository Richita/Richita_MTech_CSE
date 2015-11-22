/**
 * 
 */
package com.microkernel.core.state;

import java.util.List;

import com.microkernel.core.Service;
import com.microkernel.core.ServiceContext;
import com.microkernel.core.flow.ServiceExecutor;
import com.microkernel.core.flow.State;
import com.microkernel.core.flow.StateExecutionStatus;

/**
 * @author NinadIngole
 *
 */
public abstract class AbstractState implements State {

	private final String name;
	private List<Service<?>> services;

	public AbstractState(String name) {
		super();
		this.name = name;
	}

	public AbstractState(String name,List<Service<?>> services){
		this.name = name;
		this.services = services;
	}

	public String getName() {
		return this.name;
	}

	public abstract StateExecutionStatus handle(ServiceExecutor executor,ServiceContext context);

	public void setServices(List<Service<?>> services){this.services = services;}

	public List<Service<?>> getServices(){return this.services;}

}
