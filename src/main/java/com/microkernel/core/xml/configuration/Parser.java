package com.microkernel.core.xml.configuration;

import org.w3c.dom.Element;

public interface Parser<Q> {

	Q parse(Element element, Builder builder);
	
}
