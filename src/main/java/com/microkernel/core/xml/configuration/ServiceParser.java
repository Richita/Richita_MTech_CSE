package com.microkernel.core.xml.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Element;

import com.microkernel.core.Service;
import com.microkernel.core.ServiceLocator;
import com.microkernel.core.xml.Parser;

/**
 * Created by NinadIngole on 9/15/2015.
 */
public class ServiceParser implements Parser<Service<?>> {
   
	private final String ATTR_REF = "ref";
	
	ServiceLocator serviceLocator;
	
	
	
    public ServiceLocator getServiceLocator() {
		return serviceLocator;
	}



	public void setServiceLocator(ServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator;
	}



	public Service<?> parse(Element element) {
		String serviceBeanName = element.getAttribute(ATTR_REF);
		Service<?> service = this.serviceLocator.getService(serviceBeanName);
		if( null == service ){
			throw new IllegalArgumentException("Service bean with name "+serviceBeanName+" does not exists. Please check application-context for the bean instance id.");
		}		return service;
    }
}
