package com.microkernel.core.service.locator;

import java.util.HashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;

import com.microkernel.core.ServiceLocator;
import com.microkernel.core.Service;

public class SimpleServiceLocator implements ServiceLocator, Ordered,BeanPostProcessor{

	private HashMap<String, Service<?>> services = new HashMap<String, Service<?>>();
	
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		Class<?> clazz = bean.getClass();
		Class[] interfaces = clazz.getInterfaces();
		
		for(Class interfaze : interfaces){
			if(interfaze == Service.class){
				Service<?> service = (Service) bean;
				putService(beanName, service);
			}
		}
		
		
		return bean;
	}

	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		
		return bean;
	}

	public Service<?> getService(String name) {
		return this.services.get(name);
	}

	public void putService(String beanName, Service<?> service) {
		this.services.put(beanName, service);
	}

	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}

	

}
