package com.microkernel.core.xml.configuration;

import com.microkernel.core.flow.Flow;
import com.microkernel.core.xml.Parser;
import com.microkernel.core.xml.ParserContext;
import com.microkernel.core.xml.support.GenericParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by NinadIngole on 9/15/2015.
 */
public class MicrokernelParser implements Parser<Void> {



    @Override
    public Void parse(Element element, ParserContext context) {

        Collection<Flow> flows = new ArrayList<Flow>();

        ParserContext context1 = new GenericParserContext();

        NodeList nodes = element.getChildNodes();
        for(int i = 0; i < nodes.getLength(); i++){
            Element childElement = (Element) nodes.item(i);

            Flow flow = new FlowParser().parse(childElement,context1);

        }

        return null;
    }
}
