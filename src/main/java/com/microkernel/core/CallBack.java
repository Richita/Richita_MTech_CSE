package com.microkernel.core;

/**
 * This callback is used to send response and exception as event to the caller. Its based on event driven pattern
 * where process dont wait for the response (ie. Blocking mode) but will recieve response once processing is done
 * by the calling mechanism.
 * 
 * This is based on famous Hollywood principal "YOU DONT CALL ME ! I WILL CALL YOU !"
 * @author NinadIngole
 *
 */
public interface CallBack {

	/**
	 * On sucessfull processing of all the states the orchestrator will send response event.
	 * @param response
	 */
	public void onResponse(Object response);
	
	/**
	 * On Any failure the orchestrator will send exception details
	 * @param t
	 */
	public void onError(Throwable t);
	
}
