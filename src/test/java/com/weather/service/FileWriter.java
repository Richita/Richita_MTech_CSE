package com.weather.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microkernel.core.Service;
import com.microkernel.core.ServiceContext;
import com.weather.xml1.CurrentWeather;

public class FileWriter implements Service<String> {
	private Logger log = LoggerFactory.getLogger(FileWriter.class);
	private String fileName;
	
	
	

	@Override
	public void process(String data, ServiceContext context) throws Exception {
		List<CurrentWeather> allWeather = context.get("PERSIST_DATA");
		
		java.io.FileWriter writer = new java.io.FileWriter(getFileName());
		writer.write("CITY,LOCATION,TIME,WIND,VISIBILITY,TEMPERATURE,DEWPOINT,RELATIVEHUMIDITY,PRESSURE,STATUS"+System.lineSeparator());
		for(CurrentWeather weather : allWeather){
			log.info("Writing to File : "+weather.getCity());
			writer.write(weather.toString());
			
		}
		writer.close();
		
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
