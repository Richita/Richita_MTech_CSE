package com.microkernel.core.xml.configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.microkernel.core.flow.Flow;
import com.microkernel.core.flow.FlowHolder;
import com.microkernel.core.flow.support.FlowHolderImpl;
import com.microkernel.core.util.Assert;
import com.microkernel.core.xml.Parser;
import com.microkernel.core.xml.ProcessDefinitionParser;

/**
 * Implementation of ProcessDefinitionParser to parse the entire process-def
 * Created by NinadIngole on 9/15/2015.
 */
public class MicrokernelProcessDefinitionParser implements  ProcessDefinitionParser{

	@Autowired
	Parser<Flow> parser;
	
	
	
	public Parser<Flow> getParser() {
		return parser;
	}

	public void setParser(Parser<Flow> parser) {
		this.parser = parser;
	}

	public FlowHolder parseXML(File f) {
		FlowHolder holder = null;
		
		// Validate XML file with XSD
		
		// parse File
		
		try {
			holder = parse(f);
		} catch (ParserConfigurationException e) {
			
			e.printStackTrace();
		} catch (SAXException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return holder;
	}

	@SuppressWarnings("unused")
	private static final String MICROKERNEL_TAG = "microkernel";
	
	private static final String FLOW_TAG = "flow";
	
	private FlowHolder parse(File f) throws ParserConfigurationException, SAXException, IOException{
		FlowHolder holder = null;
		
		Collection<Flow> flows = new ArrayList<Flow>();
		
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
		Document parsedDocument = documentBuilder.parse(f);
		parsedDocument.getDocumentElement().normalize();
		
		NodeList elements = parsedDocument.getElementsByTagName(FLOW_TAG);
		
		for(int i = 0; i < elements.getLength(); i++){
			
			Element item = (Element) elements.item(i);
			
			Flow flow = parser.parse(item);
			
			
			Assert.notNull(flow, "Flow Object is Null");
			
			flows.add(flow);
			
		}
		
		holder = new FlowHolderImpl(flows);
		
		return holder;
	}


   
}
