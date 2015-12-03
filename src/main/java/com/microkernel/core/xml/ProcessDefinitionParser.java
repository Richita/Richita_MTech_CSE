package com.microkernel.core.xml;

import java.io.File;

import com.microkernel.core.flow.FlowHolder;

/**
 * ProcessDefinitionParser is responsible for parsing the xml which generates the java objects 
 * i.e State Transition machine that will be then processed.
 * @author NinadIngole
 *
 */
public interface ProcessDefinitionParser {

	FlowHolder parseXML(File f);
	
}
