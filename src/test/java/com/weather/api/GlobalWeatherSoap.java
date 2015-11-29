package com.weather.api;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.2
 * 2015-11-22T16:05:31.996+05:30
 * Generated source version: 3.1.2
 * 
 */
@WebService(targetNamespace = "http://www.webserviceX.NET", name = "GlobalWeatherSoap")
@XmlSeeAlso({ObjectFactory.class})
public interface GlobalWeatherSoap {

    /**
     * Get all major cities by country name(full / part).
     */
    @WebMethod(operationName = "GetCitiesByCountry", action = "http://www.webserviceX.NET/GetCitiesByCountry")
    @RequestWrapper(localName = "GetCitiesByCountry", targetNamespace = "http://www.webserviceX.NET", className = "com.weather.api.GetCitiesByCountry")
    @ResponseWrapper(localName = "GetCitiesByCountryResponse", targetNamespace = "http://www.webserviceX.NET", className = "com.weather.api.GetCitiesByCountryResponse")
    @WebResult(name = "GetCitiesByCountryResult", targetNamespace = "http://www.webserviceX.NET")
    public java.lang.String getCitiesByCountry(
        @WebParam(name = "CountryName", targetNamespace = "http://www.webserviceX.NET")
        java.lang.String countryName
    );

    /**
     * Get weather report for all major cities around the world.
     */
    @WebMethod(operationName = "GetWeather", action = "http://www.webserviceX.NET/GetWeather")
    @RequestWrapper(localName = "GetWeather", targetNamespace = "http://www.webserviceX.NET", className = "com.weather.api.GetWeather")
    @ResponseWrapper(localName = "GetWeatherResponse", targetNamespace = "http://www.webserviceX.NET", className = "com.weather.api.GetWeatherResponse")
    @WebResult(name = "GetWeatherResult", targetNamespace = "http://www.webserviceX.NET")
    public java.lang.String getWeather(
        @WebParam(name = "CityName", targetNamespace = "http://www.webserviceX.NET")
        java.lang.String cityName,
        @WebParam(name = "CountryName", targetNamespace = "http://www.webserviceX.NET")
        java.lang.String countryName
    );
}
