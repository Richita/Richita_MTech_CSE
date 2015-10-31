package com.dummy.service;

import com.microkernel.core.ServiceContext;
import com.microkernel.core.service.Service;

public class HelloWorldService implements Service<String>{

	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}



	public void execute(String request, ServiceContext context) {
		System.out.println("Service executed");
	}
	
}