package com.weather.service;

import java.io.StringReader;
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

import com.microkernel.core.Service;
import com.microkernel.core.ServiceContext;
import com.weather.api.GlobalWeather;
import com.weather.api.GlobalWeatherSoap;
import com.weather.xml.NewDataSet.Table;
import com.weather.xml1.CurrentWeather;

public class TempratureService implements Service<String> {

	private SessionFactory session;
	
	@Override
	public String getName() {
		
		return "cityTempratureService";
	}

	@Override
	public void process(String data, ServiceContext context) throws Exception {
		
		List<Table> cities = context.get("CITIES");
		Map<String, CurrentWeather> weatherByCities = new HashMap<String, CurrentWeather>();
		
		GlobalWeather w = new GlobalWeather();
		GlobalWeatherSoap soap = w.getGlobalWeatherSoap();
		JAXBContext instance = JAXBContext.newInstance(CurrentWeather.class);
		Unmarshaller umarshaller = instance.createUnmarshaller();
		
		for(Table t: cities) {
			String weather = soap.getWeather(t.getCity(), t.getCountry());
			JAXBElement<CurrentWeather> weatherElement = null;
			if(!weather.equalsIgnoreCase("data not found")) {
				weatherElement = umarshaller.unmarshal(new StreamSource(new StringReader(weather)),
					CurrentWeather.class);
			}
			CurrentWeather currentWeather = null;
			if(null != weatherElement)
			{
				currentWeather = weatherElement.getValue();
				currentWeather.setCity(t.getCity());
				Session openSession = session.openSession();
				Transaction transaction = openSession.beginTransaction();
				openSession.persist(currentWeather);
				transaction.commit();
				openSession.close();

				
			}

//			System.out.println(t.getCity()+" = "+currentWeather);
			weatherByCities.put(t.getCity(), currentWeather);
		}
		context.setResponse("DONE");
	}

	public SessionFactory getSession() {
		return session;
	}

	public void setSession(SessionFactory session) {
		this.session = session;
	}

}
