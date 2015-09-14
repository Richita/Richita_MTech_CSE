/**
 * 
 */
package com.microkernel.core.flow.support;

import org.apache.commons.lang.StringUtils;

import com.microkernel.core.flow.State;
import com.microkernel.core.util.Assert;

/**
 * State Transition has details about a state and the next state to which it
 * moves when certain condition is satisfied using pattern
 * 
 * @author NinadIngole
 * @version 1.0
 */
public final class StateTransition {

	private final State state;
	private final String pattern;
	private final String next;

	public StateTransition(State state,String pattern, String next) {
		Assert.notNull(state);
		
		if (StringUtils.isEmpty(pattern)) {
			this.pattern = "*";
		} else {
			this.pattern = pattern;
		}
		this.state = state;
		this.next = next;
	}

	public State getState() {
		return state;
	}

	public String getPattern() {
		return pattern;
	}

	public String getNext() {
		return next;
	}
	
	public boolean isEnd(){
		return next == null;
	}
	
	
}
