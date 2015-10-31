package com.microkernel.core.flow.support;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.microkernel.core.flow.Flow;
import com.microkernel.core.flow.FlowHolder;
import com.microkernel.core.xml.ProcessDefinitionParser;

public class FlowHolderImpl implements FlowHolder,InitializingBean {

	@Autowired
	private ProcessDefinitionParser parser;
	
	private String processDefinitionLoc;
	
	
	
	public String getProcessDefinitionLoc() {
		return processDefinitionLoc;
	}

	public void setProcessDefinitionLoc(String processDefinitionLoc) {
		this.processDefinitionLoc = processDefinitionLoc;
	}

	public ProcessDefinitionParser getParser() {
		return parser;
	}

	public void setParser(ProcessDefinitionParser parser) {
		this.parser = parser;
	}


	private HashMap<String, Flow> flows = new HashMap<String, Flow>();
	
	public FlowHolderImpl(){}
	
	public FlowHolderImpl(Collection<Flow> flows) {
		parseFlows(flows);
	}
	
	private void parseFlows(Collection<Flow> flows) {
		for(Flow flow: flows){
			this.flows.put(flow.getName(), flow);
		}
	}

	public Flow getFlow(String name) {
		return this.flows.get(name);
	}


	public Collection<Flow> getFlows() {
		return this.flows.values();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(null == parser)
			throw new IllegalArgumentException("Parser is Not set cannot parse process-definition.");
		
		File f = new File(this.getClass().getClassLoader().getResource(processDefinitionLoc).toString());
		if(f.exists()){
			FlowHolder holder = parser.parseXML(f);
			parseFlows(holder.getFlows());
		}
		
	}
}
