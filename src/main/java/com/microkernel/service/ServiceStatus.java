package com.microkernel.service;


public class ServiceStatus {
	
	private final String status;
	
	private String message;
	
	public static final ServiceStatus STARTED = new ServiceStatus("STARTED", null);
	
	public static final ServiceStatus COMPLETED = new ServiceStatus("COMPLETED", null);
	
	public static final ServiceStatus FAILED = new ServiceStatus("FAILED", null);
	
	private ServiceStatus(String status,String message){
		
		this.status = status;
		this.message = message;
	}

}
