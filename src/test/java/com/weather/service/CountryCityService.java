package com.weather.service;


import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import com.microkernel.core.Service;
import com.microkernel.core.ServiceContext;
import com.weather.api.GlobalWeather;
import com.weather.api.GlobalWeatherSoap;
import com.weather.xml.NewDataSet;
import com.weather.xml.NewDataSet.Table;
import com.weather.xml1.CurrentWeather;

public class CountryCityService implements Service<String> {

	@Override
	public String getName() {
		
		return "countryCityService";
	}

	@Override
	public void process(String countryName, ServiceContext context) throws Exception {
		
		GlobalWeather w = new GlobalWeather();
		GlobalWeatherSoap soap = w.getGlobalWeatherSoap();
		String citiesByCountry = soap.getCitiesByCountry(countryName);
		
		JAXBContext instance = JAXBContext.newInstance(NewDataSet.class);
		Unmarshaller um = instance.createUnmarshaller();
		JAXBElement<NewDataSet> parsedOutput = um.unmarshal(new StreamSource(new StringReader(citiesByCountry)),NewDataSet.class);
		List<Table> table = parsedOutput.getValue().getTable();
		
		context.add("CITIES", table);
		
		
	}

}