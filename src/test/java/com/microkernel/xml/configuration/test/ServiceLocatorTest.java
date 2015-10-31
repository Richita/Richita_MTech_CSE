package com.microkernel.xml.configuration.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.microkernel.core.ServiceLocator;
import com.microkernel.core.service.Service;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class ServiceLocatorTest {

	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		
		
		
	}

}
