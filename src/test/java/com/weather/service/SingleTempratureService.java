package com.weather.service;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microkernel.core.Service;
import com.microkernel.core.ServiceContext;
import com.weather.api.GlobalWeather;
import com.weather.api.GlobalWeatherSoap;
import com.weather.xml.NewDataSet.Table;
import com.weather.xml1.CurrentWeather;

public class SingleTempratureService implements Service<String> {

	private Logger log = LoggerFactory.getLogger(SingleTempratureService.class);

	private SessionFactory session;
	JAXBContext instance = null;
	Unmarshaller umarshaller = null;

	public SingleTempratureService() {
		try {
			instance = JAXBContext.newInstance(CurrentWeather.class);
			umarshaller = instance.createUnmarshaller();
		} catch (JAXBException e) {
			log.error(e.getMessage(),e);
		}
	}

	

	@Override
	public void process(String data, ServiceContext context) throws Exception {
		StringTokenizer token = new StringTokenizer(data, ",");
		String city = null;
		String country = null;

		country = token.nextToken();
		city = token.nextToken();
		log.info("Getting Temprature for " + city + " - " + country);
		GlobalWeather g = new GlobalWeather();
		GlobalWeatherSoap soap = g.getGlobalWeatherSoap();
		String weather = soap.getWeather(city, country);

		if (!weather.equalsIgnoreCase("data not found")) {
			JAXBElement<CurrentWeather> weatherJAXB = umarshaller.unmarshal(new StreamSource(new StringReader(weather)),
					CurrentWeather.class);

			CurrentWeather value = weatherJAXB.getValue();

			value.setCity(city);

			Session openSession = session.openSession();
			openSession.beginTransaction();
			openSession.persist(value);
			log.info("Data Saved to Database");
			openSession.getTransaction().commit();
			openSession.close();
			
			context.setResponse(value);
		}else{
			context.setResponse(new Object());
		}
		
	}

	public SessionFactory getSession() {
		return session;
	}

	public void setSession(SessionFactory session) {
		this.session = session;
	}

}
