package com.dummy.service;

import com.microkernel.core.Service;

public class HelloWorldService implements Service<String>{

	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void process(String data) {
		System.out.println("Hello World");
	}

	



	
}