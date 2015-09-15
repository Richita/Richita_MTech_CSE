/**
 * 
 */
package com.microkernel.core.flow.support;

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

	private StateTransition(State state,String pattern, String next) {
		Assert.notNull(state);
		

		this.state = state;
		this.next = next;
		this.pattern = pattern;
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


	public static StateTransition createStateTransition(State state, String pattern, String next){
		return new StateTransition(state,pattern,next);
	}

	public static StateTransition createEndStateTransition(State state,String pattern){
		return createStateTransition(state,pattern,null);
	}
	
}
