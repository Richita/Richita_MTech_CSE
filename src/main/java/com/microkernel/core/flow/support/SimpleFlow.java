package com.microkernel.core.flow.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microkernel.core.ServiceContext;
import com.microkernel.core.flow.Flow;
import com.microkernel.core.flow.ServiceExecutor;
import com.microkernel.core.flow.State;
import com.microkernel.core.flow.StateExecutionStatus;

/**
 * Implementaton of Flow Interface
 * 
 * @author NinadIngole
 *
 */
public class SimpleFlow implements Flow {

	Logger log = LoggerFactory.getLogger(SimpleFlow.class);

	private final String name;

	private final String STATUS_KEY = "status";

	
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
		log.info("initiating transitions mappings");
		startState = null;
		transitionMap.clear();
		stateMap.clear();
		boolean hasEndState = false;

		for (StateTransition transition : transitions) {
			stateMap.put(transition.getState().getName(), transition.getState());
		}

		for (StateTransition transition : transitions) {
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

		if (!hasEndState) {
			throw new IllegalArgumentException("No End State to the transition");
		}
		startState = transitions.get(0).getState();
		log.info("flow " + name + " initiated...");
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

	public void start(ServiceContext context) {
		State state = this.startState;
		StateExecutionStatus status = StateExecutionStatus.UNKNOWN;

		while (isFlowContinue(state, status)) {
			log.info("Executing State = " + state.getName());
			try {
				status = state.handle(executor, context);
			} catch (TimeoutException e) {

				status = StateExecutionStatus.FAIL;
				log.error(e.getMessage(), e);
			} finally {

			}
			state = doNext(state, status);
		}
		
		context.add(STATUS_KEY, status);
	}

	private State doNext(State state, StateExecutionStatus status) {
		Set<StateTransition> set = this.transitionMap.get(state.getName());
		for (StateTransition transition : set) {
			if (transition.matches(status.getName())) {
				String next = transition.getNext();
				State state2 = stateMap.get(next);
				return state2;
			}
		}
		return null;
	}

	private boolean isFlowContinue(State state, StateExecutionStatus status) {
		if (null == state || status.isStop()) {
			log.info("End of flow " + name + ".");
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "SimpleFlow [name=" + name + ", transitionMap=" + transitionMap + "]";
	}

}
