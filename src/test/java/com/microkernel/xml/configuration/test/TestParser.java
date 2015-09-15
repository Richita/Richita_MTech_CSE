package com.microkernel.xml.configuration.test;

import com.microkernel.core.flow.Flow;
import com.microkernel.core.xml.configuration.MicrokernelParser;
import com.microkernel.core.xml.support.GenericParserContext;
import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by NinadIngole on 9/15/2015.
 */
public class TestParser {

    @Test
    public void testParsing() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactoryImpl.newInstance();
        DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
        Document doc = documentBuilder.parse(TestParser.class.getClassLoader().getResource("process-definition.xml").getFile());
        doc.getDocumentElement().normalize();


        Element root = (Element) doc.getDocumentElement();

        Collection<Flow> parse = new MicrokernelParser().parse(root, new GenericParserContext());

        System.out.println(parse);
    }
}
