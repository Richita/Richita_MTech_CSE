package com.microkernel.core.xml;

import org.w3c.dom.Element;

/**
 * Parser Interface implemented by all the parser components
 * @author NinadIngole
 *
 * @param <Q>
 */
public interface Parser<Q> {

	Q parse(Element element);
	
}
