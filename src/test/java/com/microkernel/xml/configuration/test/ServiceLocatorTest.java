package com.microkernel.xml.configuration.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.microkernel.core.Service;
import com.microkernel.core.ServiceLocator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class ServiceLocatorTest {

	@Autowired
	ServiceLocator serviceLocator;
	
	
	
	public ServiceLocator getServiceLocator() {
		return serviceLocator;
	}

	public void setServiceLocator(ServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator;
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		
		Service<?> service = this.serviceLocator.getService("HelloWorld");
//		service.process();
		
	}

}
