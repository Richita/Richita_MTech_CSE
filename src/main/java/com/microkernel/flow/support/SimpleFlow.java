package com.microkernel.flow.support;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import com.microkernel.flow.Flow;
import com.microkernel.flow.State;

public class SimpleFlow implements Flow {

	private final String name;
	
	private Collection<StateTransition> transitions;

	private HashMap<String, Set<StateTransition>> transitionMap = new HashMap<String, Set<StateTransition>>();
	
	private HashMap<String, State> stateMap = new HashMap<String, State>();
	
	private State startState;
	
	
	
	public SimpleFlow(String name, Collection<StateTransition> transitions) {
		super();
		this.name = name;
		this.transitions = transitions;
	}

	public String getName() {
		return this.name;
	}

	public void setStateTransitions(Collection<StateTransition> transitions) {
		this.transitions = transitions;
		
	}
	
	

	public Collection<State> getStates() {
		return this.stateMap.values();
	}

	public void start() {
		// TODO Auto-generated method stub
		
	}

}
