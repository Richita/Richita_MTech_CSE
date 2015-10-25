package com.microkernel.xml.configuration.test;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.xml.sax.SAXException;

import com.microkernel.core.flow.FlowHolder;
import com.microkernel.core.xml.ProcessDefinitionParser;
import com.microkernel.core.xml.configuration.MicrokernelProcessDefinitionParser;

/**
 * Created by NinadIngole on 9/15/2015.
 */
public class TestParser {

    @Test
    public void testParsing() throws ParserConfigurationException, IOException, SAXException {
    	
    	ProcessDefinitionParser parser = new MicrokernelProcessDefinitionParser();
    	ClassPathResource processDefFile = new ClassPathResource("process-definition.xml");
    
    	FlowHolder parseXML = parser.parseXML(processDefFile.getFile());
     
    }
}
