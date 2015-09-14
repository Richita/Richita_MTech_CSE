package com.microkernel.core.xml.configuration;

import org.w3c.dom.Element;

public interface Parser<Q,K> {

	Q parse(Element element, Builder<K> builder);
	
}
