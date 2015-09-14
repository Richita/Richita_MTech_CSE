/**
 * 
 */
package com.microkernel.core.state;

import com.microkernel.core.flow.State;

/**
 * @author NinadIngole
 *
 */
public abstract class AbstractState implements State {

	private final String name;
	private final boolean isEnd;

	public AbstractState(String name, boolean isEnd) {
		super();
		this.name = name;
		this.isEnd = isEnd;
	}

	public String getName() {
		return this.name;
	}

	public abstract void handle();

	public boolean isEnd() {
		return this.isEnd;
	}

}
