package com.microkernel.core.flow;

/**
 * State Executions Status which is used to derive the next state to execute.
 * @author NinadIngole
 *
 */
public class StateExecutionStatus implements Comparable<StateExecutionStatus> {
	
	public static final StateExecutionStatus COMPLETED = new StateExecutionStatus(Status.COMPLETED.toString());
	
	public static final StateExecutionStatus FAIL = new StateExecutionStatus(Status.FAIL.toString());
	
	public static final StateExecutionStatus STOPPED = new StateExecutionStatus(Status.STOPPED.toString());
	
	public static final StateExecutionStatus UNKNOWN = new StateExecutionStatus(Status.UNKNOWN.toString());
	
	
	
	private String name;
	
	private StateExecutionStatus(String status) {
		this.name = status;
	}
	
	private enum Status{
		COMPLETED, STOPPED, FAIL, UNKNOWN;
		
		static Status match(String value) {
			for(int i = 0; i < values().length; i++) {
				Status status = values()[i];
				if(value.startsWith(status.toString())) {
					return status;
				}
			}
			return COMPLETED;
		}
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean isStop() {
		return name.startsWith(STOPPED.getName());
	}
	
	public boolean isEnd() {
		return isStop() || isFail() || isComplete();
	}
	
	public boolean isComplete() {
		return name.startsWith(COMPLETED.getName());
	}
	
	public boolean isFail() {
		return name.startsWith(FAIL.getName());
	}

	@Override
	public int compareTo(StateExecutionStatus other) {
		Status one = Status.match(this.name);
		Status two = Status.match(other.getName());
		int comparison = one.compareTo(two);
		if(comparison == 0 ) {
			return this.name.compareTo(other.name);
		}
		return comparison;
	}

	@Override
	public String toString() {
		return "StateExecutionStatus [name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		StateExecutionStatus other = (StateExecutionStatus) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	
}
