package com.weather.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.microkernel.core.Service;
import com.microkernel.core.ServiceContext;
import com.weather.api.GlobalWeather;
import com.weather.api.GlobalWeatherSoap;
import com.weather.xml.NewDataSet.Table;
import com.weather.xml1.CurrentWeather;

public class TempratureService implements Service<String> {
	private Logger log = LoggerFactory.getLogger(TempratureService.class);
	private SessionFactory session;

	

	@Override
	public void process(String data, ServiceContext context) throws Exception {

		List<Table> cities = context.get("CITIES");
		ArrayList<CurrentWeather> allWeather = new ArrayList<CurrentWeather>();

		GlobalWeather w = new GlobalWeather();
		GlobalWeatherSoap soap = w.getGlobalWeatherSoap();
		JAXBContext instance = JAXBContext.newInstance(CurrentWeather.class);
		Unmarshaller umarshaller = instance.createUnmarshaller();

		for (Table t : cities) {
			String weather = soap.getWeather(t.getCity(), t.getCountry());
			JAXBElement<CurrentWeather> weatherElement = null;
			if (!weather.equalsIgnoreCase("data not found")) {
				log.info("Data Not Found for City:"+t.getCity());
				weatherElement = umarshaller.unmarshal(new StreamSource(new StringReader(weather)),
						CurrentWeather.class);
			}
			CurrentWeather currentWeather = null;
			log.info("Data Found for: "+t.getCity());
			if (null != weatherElement) {
				currentWeather = weatherElement.getValue();
				currentWeather.setCity(t.getCity());
				allWeather.add(currentWeather);
			}
		}
		
		context.add("PERSIST_DATA", allWeather);

	}

	public SessionFactory getSession() {
		return session;
	}

	public void setSession(SessionFactory session) {
		this.session = session;
	}

}
