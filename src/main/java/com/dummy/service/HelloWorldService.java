package com.dummy.service;

import com.microkernel.core.Service;
import com.microkernel.core.ServiceContext;

public class HelloWorldService implements Service<String>{

	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	

	@Override
	public void process(String data, ServiceContext context) throws Exception {
		System.out.println("Hello World");
		
	}

	



	
}