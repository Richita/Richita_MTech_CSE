package com.microkernel.core;

public interface CallBack {

	public void onResponse(Object response);
	
	public void onError(Throwable t);
	
}
