package com.weather.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microkernel.core.Service;
import com.microkernel.core.ServiceContext;
import com.weather.xml1.CurrentWeather;

public class DBWriter implements Service<String> {
	private Logger log = LoggerFactory.getLogger(DBWriter.class);
	
	private SessionFactory session;
	
	
	
	
	@Override
	public void process(String data, ServiceContext context) throws Exception {
		List<CurrentWeather> allWeather = context.get("PERSIST_DATA");
		Session openSession = session.openSession();
		Transaction transaction = openSession.getTransaction();
		try{
			transaction.begin();
		for(CurrentWeather weather : allWeather){
			openSession.persist(weather);
			log.info("DBSUCESS: "+weather.getCity());
		}
			transaction.commit();
		}catch(Exception e){
			log.error(e.getMessage(),e);
			transaction.rollback();
		}finally{
			log.info(transaction.getStatus().toString());
			openSession.close();
		}
		
	}

	public SessionFactory getSession() {
		return session;
	}

	public void setSession(SessionFactory session) {
		this.session = session;
	}

	

}
