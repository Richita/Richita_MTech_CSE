package com.microkernel.core;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by NinadIngole on 9/15/2015.
 */
public class ServiceContext {

	public final static String REQUEST = "request";
	public final static String RESPONSE = "response";
	
	private ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<String, Object>();
	private Throwable t = null;
	
	public void setException(Throwable t){
		this.t = t;
	}
	
	public Throwable getException(){
		return t;
	}
	
	public void setRequest(Object request){
		this.map.put(REQUEST, request);
	}
	
	public void setResponse(Object response){
		this.map.put(RESPONSE, response);
	}
	
	public void clear(){
		map.clear();
		t = null;
	}
	
	public Object getRequest() {
		return map.get(REQUEST);
	}
	
	public Object getResponse(){
		return map.get(RESPONSE);
	}
	
	public <T> T get(String key) {
		return (T) map.get(key);
	}
	
	public void add(String key,Object value) {
		this.map.put(key, value);
	}
}
