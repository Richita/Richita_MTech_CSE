package com.microkernel.core;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by NinadIngole on 9/15/2015.
 */
public class ServiceContext {

	public final static String REQUEST = "request";
	public final static String RESPONSE = "response";
	
	private ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<String, Object>();
	
	public void setRequest(Object request){
		this.map.put(REQUEST, request);
	}
	
	public void setResponse(Object response){
		this.map.put(RESPONSE, response);
	}
	
	public void clear(){
		map.clear();
	}
	
	public Object getRequest() {
		return map.get(REQUEST);
	}
	
	public Object getResponse(){
		return map.get(RESPONSE);
	}
}
