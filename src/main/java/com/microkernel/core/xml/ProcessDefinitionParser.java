package com.microkernel.core.xml;

import java.io.File;

import com.microkernel.core.flow.FlowHolder;

public interface ProcessDefinitionParser {

	FlowHolder parseXML(File f);
	
}
