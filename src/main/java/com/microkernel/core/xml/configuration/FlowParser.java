package com.microkernel.core.xml.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.microkernel.core.flow.Flow;
import com.microkernel.core.flow.ServiceExecutor;
import com.microkernel.core.flow.support.SimpleFlow;
import com.microkernel.core.flow.support.StateTransition;
import com.microkernel.core.xml.Parser;

public class FlowParser implements Parser<Flow> {

	@Autowired
	Parser<StateTransition> parser;
	
	@Autowired
	ServiceExecutor executor;
	
	
	
	public ServiceExecutor getExecutor() {
		return executor;
	}

	public void setExecutor(ServiceExecutor executor) {
		this.executor = executor;
	}

	public Parser<StateTransition> getParser() {
		return parser;
	}

	public void setParser(Parser<StateTransition> parser) {
		this.parser = parser;
	}

	private final String ATTR_ID = "id";
	
	private final String ELE_STATE = "state";

	public Flow parse(Element element) {

		String flowName = element.getAttribute(ATTR_ID);
		List<StateTransition> stateTransitions = new ArrayList<StateTransition>();

		NodeList executors = element.getElementsByTagName(ELE_STATE);

		for(int i = 0 ; i < executors.getLength(); i++){
			StateTransition transition = parser.parse((Element) executors.item(i));
			if(transition != null)
				stateTransitions.add(transition);
		}
		SimpleFlow flow = new SimpleFlow(flowName,stateTransitions);
		flow.setExecutor(executor);
		return flow;
	}

}
