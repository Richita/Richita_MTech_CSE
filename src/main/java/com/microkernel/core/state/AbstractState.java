/**
 * 
 */
package com.microkernel.core.state;

import java.util.List;
import java.util.concurrent.TimeoutException;

import com.microkernel.core.Service;
import com.microkernel.core.ServiceContext;
import com.microkernel.core.flow.ServiceExecutor;
import com.microkernel.core.flow.State;
import com.microkernel.core.flow.StateExecutionStatus;

/**
 * 
 * This class abstracts the details used by both the Sequential State and ParallelState
 * In future to add new execution mechanism one can extend this class to get injection of 
 * basic dependencies
 * 
 * @author NinadIngole
 *
 */
public abstract class AbstractState implements State {

	/**
	 * Name of the State which is represented by id in process-def.xml
	 */
	private final String name;
	/**
	 * All the services that needs to be executed in this State
	 * in process-def.xml services are given in <service ref="" /> tag
	 */
	private List<Service<?>> services;
	
	/**
	 * The timeout after which the the service should be cancelled as the executor
	 * is not going wait for Infinite time and will expire the thread manually so that
	 * resouces/threads will get released.
	 */
	// TODO Default timing for a service to execute.
	private long timeout = Long.MAX_VALUE;
	
	public AbstractState(String name) {
		super();
		this.name = name;
	}

	public AbstractState(String name,List<Service<?>> services){
		this.name = name;
		this.services = services;
	}
	
	public AbstractState(String name,List<Service<?>> services,Long timeout){
		this.name = name;
		this.services = services;
		this.timeout = timeout;
	}

	public String getName() {
		return this.name;
	}

	public abstract StateExecutionStatus handle(ServiceExecutor executor,ServiceContext context) throws TimeoutException;

	public void setServices(List<Service<?>> services){this.services = services;}

	public List<Service<?>> getServices(){return this.services;}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((services == null) ? 0 : services.hashCode());
		result = prime * result + (int) (timeout ^ (timeout >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractState other = (AbstractState) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (services == null) {
			if (other.services != null)
				return false;
		} else if (!services.equals(other.services))
			return false;
		if (timeout != other.timeout)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AbstractState [name=" + name + ", services=" + services + ", timeout=" + timeout + "]";
	}

	
}
