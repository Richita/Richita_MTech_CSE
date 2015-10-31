package com.microkernel.core.flow.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.microkernel.core.flow.Flow;
import com.microkernel.core.flow.State;

public class SimpleFlow implements Flow {

	private final String name;
	
	private List<StateTransition> transitions = new ArrayList<StateTransition>();

	private HashMap<String, Set<StateTransition>> transitionMap = new HashMap<String, Set<StateTransition>>();
	
	private HashMap<String, State> stateMap = new HashMap<String, State>();
	
	private State startState;

	
	
	public SimpleFlow(String name, List<StateTransition> transitions) {
		super();
		this.name = name;
		this.transitions = transitions;
		initiateTransitions();
	}

	private void initiateTransitions() {
		startState = null;
		transitionMap.clear();
		stateMap.clear();
		boolean hasEndState = false;

		for(StateTransition transition : transitions){
			stateMap.put(transition.getState().getName(),transition.getState());
		}

		for(StateTransition transition: transitions) {
			State state = transition.getState();

			if (!transition.isEnd()) {
				String next = transition.getNext();
				if (!stateMap.containsKey(next)) {
					throw new IllegalArgumentException("Missing State : " + next);
				}
			} else {
				hasEndState = true;
			}

			String name = state.getName();

			Set<StateTransition> set = transitionMap.get(name);
			if (set == null) {
				set = new LinkedHashSet<StateTransition>();

				transitionMap.put(name, set);
			}

			set.add(transition);
		}

		if(!hasEndState){
			throw new IllegalArgumentException("No End State to the transition");
		}
		startState = transitions.get(0).getState();
	}

	public String getName() {
		return this.name;
	}

	public void setStateTransitions(List<StateTransition> transitions) {
		this.transitions = transitions;
		
	}
	
	

	public Collection<State> getStates() {
		return this.stateMap.values();
	}

	public void start() {
		// TODO Auto-generated method stub
		
	}


}
