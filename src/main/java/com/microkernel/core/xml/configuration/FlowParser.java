package com.microkernel.core.xml.configuration;

import com.microkernel.core.flow.support.SimpleFlow;
import com.microkernel.core.flow.support.StateTransition;
import com.microkernel.core.xml.Parser;
import com.microkernel.core.xml.ParserContext;
import org.w3c.dom.Element;

import com.microkernel.core.flow.Flow;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Collection;

public class FlowParser implements Parser<Flow> {

	private final String ATTR_ID = "id";


	public Flow parse(Element element, ParserContext context) {

		String flowName = element.getAttribute(ATTR_ID);
		Collection<StateTransition> stateTransitions = new ArrayList<StateTransition>();

		NodeList executors = element.getElementsByTagName("executor");

		for(int i = 0 ; i < executors.getLength(); i++){
			StateTransition transition = new ExecutorParser().parse((Element) executors.item(i), context);
			if(transition != null)
				stateTransitions.add(transition);
		}

		return new SimpleFlow(flowName,stateTransitions);
	}

}
