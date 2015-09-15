package com.microkernel.core.xml;

import com.microkernel.core.xml.ParserContext;
import org.w3c.dom.Element;

public interface Parser<Q> {

	Q parse(Element element, ParserContext context);
	
}
