/**
 * 
 */
package com.microkernel.core.flow.support;

import com.microkernel.core.flow.State;
import com.microkernel.core.util.Assert;

/**
 * State Transition has details about a state and the next state to which it
 * moves when certain condition is satisfied using pattern
 * Same State Transition can be utilized in batch processing mode.
 * @author NinadIngole
 * @version 1.0
 */
public final class StateTransition {

	// current state
	private final State state;
	// pattern
	private final String pattern;
	// state name to which flow transit
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


	/**
	 * Static helper methods to create state Transition
	 * @param state
	 * @param pattern
	 * @param next
	 * @return
	 */
	public static StateTransition createStateTransition(State state, String pattern, String next){
		return new StateTransition(state,pattern,next);
	}

	public static StateTransition createEndStateTransition(State state,String pattern){
		return createStateTransition(state,pattern,null);
	}

	@Override
	public String toString() {
		return "StateTransition [state=" + state + ", pattern=" + pattern + ", next=" + next + "]";
	}
	
	
}
