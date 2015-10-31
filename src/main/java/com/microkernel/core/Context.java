package com.microkernel.core;

public interface Context<K,V> {
	V get(K key);
	
	void put(K key, V value);
	
}
