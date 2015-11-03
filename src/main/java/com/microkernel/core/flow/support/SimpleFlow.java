package com.microkernel.core.flow.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.microkernel.core.Service;
import com.microkernel.core.flow.Flow;
import com.microkernel.core.flow.FlowExecutionStatus;
import com.microkernel.core.flow.ServiceExecutor;
import com.microkernel.core.flow.State;

public class SimpleFlow implements Flow {

	private final String name;
	
	private List<StateTransition> transitions = new ArrayList<StateTransition>();

	private HashMap<String, Set<StateTransition>> transitionMap = new HashMap<String, Set<StateTransition>>();
	
	private HashMap<String, State> stateMap = new HashMap<String, State>();
	
	private State startState;

	private ServiceExecutor executor;
	
	
	public SimpleFlow(String name, List<StateTransition> transitions) {
		super();
		this.name = name;
		this.transitions = transitions;
		initiateTransitions();
	}

	
	
	public ServiceExecutor getExecutor() {
		return executor;
	}



	public void setExecutor(ServiceExecutor executor) {
		this.executor = executor;
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

	public void start(Object request) {
		State state = this.startState;
		FlowExecutionStatus status  = FlowExecutionStatus.STARTED;
		
		while(isFlowContinue(state,status)){
			
			state.handle(request,executor);
			
			
			state = doNext(state,status);
		}
		
		
		
	}

	private State doNext(State state, FlowExecutionStatus status) {
		Set<StateTransition> set = this.transitionMap.get(state.getName());
		for(StateTransition transition: set)
		{
			String next = transition.getNext();
			System.out.println(next);
			State state2 = stateMap.get(next);
			return state2;
		}
		return null;
	}

	private boolean isFlowContinue(State state, FlowExecutionStatus status) {
		if(null == state || FlowExecutionStatus.FAILED.equals(status))
			return false;
		
		return true;
	}

	

	


}
