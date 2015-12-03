package com.microkernel.core;

/**
 * Temporary storage used by states to pass processed data amongst each other
 * @author NinadIngole
 *
 * @param <K>
 * @param <V>
 */
public interface Context<K,V> {
	V get(K key);
	
	void put(K key, V value);
	
}
