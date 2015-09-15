package com.microkernel.core.xml.configuration;

import com.microkernel.core.service.Service;
import com.microkernel.core.xml.Parser;
import com.microkernel.core.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created by NinadIngole on 9/15/2015.
 */
public class ServiceParser implements Parser<Service> {
    @Override
    public Service parse(Element element, ParserContext context) {
        return null;
    }
}
