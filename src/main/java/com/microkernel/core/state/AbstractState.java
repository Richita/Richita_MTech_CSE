/**
 * 
 */
package com.microkernel.core.state;

import com.microkernel.core.flow.FlowExecutionStatus;
import com.microkernel.core.flow.FlowExecutor;
import com.microkernel.core.flow.State;
import com.microkernel.core.service.Service;

import java.util.Set;

/**
 * @author NinadIngole
 *
 */
public abstract class AbstractState implements State {

	private final String name;
	private Set<Service> services;

	public AbstractState(String name) {
		super();
		this.name = name;
	}

	public AbstractState(String name,Set<Service> services){
		this.name = name;
		this.services = services;
	}

	public String getName() {
		return this.name;
	}

	public abstract FlowExecutionStatus handle(FlowExecutor executor);

	public void setServices(Set<Service> services){this.services = services;}

	public Set<Service> getServices(){return this.services;}

}
