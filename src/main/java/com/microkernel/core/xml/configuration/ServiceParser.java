package com.microkernel.core.xml.configuration;

import org.w3c.dom.Element;

import com.microkernel.core.Service;
import com.microkernel.core.xml.Parser;

/**
 * Created by NinadIngole on 9/15/2015.
 */
public class ServiceParser implements Parser<Service> {
    @Override
    public Service parse(Element element) {
       return new Service<String>() {
    	   public String getName() {
    		   return "sample";
    	   };
    	   
    	   public void process(String data) {
    		   System.out.println("Hello World");
    	   };
	};
    }
}
