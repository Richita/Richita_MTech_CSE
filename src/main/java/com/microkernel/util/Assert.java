package com.microkernel.util;

public class Assert {
	
	public static boolean notNull(Object obj){
		if(obj == null)
			return false;
		return true;
	}

	public static void notNull(Object object, String message) {
		if(object == null){
			throw new IllegalArgumentException(message);
		}
	}

}
