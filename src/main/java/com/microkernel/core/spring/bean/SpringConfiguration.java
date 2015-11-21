package com.microkernel.core.spring.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;

public class SpringConfiguration implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		ApplicationContext appContext = event.getApplicationContext();
		final ConfigurableApplicationContext confApp = (ConfigurableApplicationContext) appContext;
		
	}

}
