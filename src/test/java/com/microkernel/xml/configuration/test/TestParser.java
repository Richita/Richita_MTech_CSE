package com.microkernel.xml.configuration.test;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xml.sax.SAXException;

import com.microkernel.core.Orchestrator;
import com.microkernel.core.flow.FlowHolder;
import com.microkernel.core.xml.ProcessDefinitionParser;
import com.microkernel.core.xml.configuration.MicrokernelProcessDefinitionParser;

/**
 * Created by NinadIngole on 9/15/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class TestParser implements ApplicationContextAware{

	
	@Autowired
	ApplicationContext appContext;
	
	
	
    public ApplicationContext getApplicationContext() {
		return appContext;
	}



	public void setApplicationContext(ApplicationContext appContext) {
		this.appContext = appContext;
	}



	@Test
    public void testParsing() throws ParserConfigurationException, IOException, SAXException {
    	
		Orchestrator bean = appContext.getBean(Orchestrator.class);
		bean.process("Hello World", "AAIS");
    	
    
     
    }
}
