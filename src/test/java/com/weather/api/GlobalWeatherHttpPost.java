package com.weather.api;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.1.2
 * 2015-11-22T16:05:32.385+05:30
 * Generated source version: 3.1.2
 * 
 */
@WebService(targetNamespace = "http://www.webserviceX.NET", name = "GlobalWeatherHttpPost")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface GlobalWeatherHttpPost {

    /**
     * Get all major cities by country name(full / part).
     */
    @WebMethod(operationName = "GetCitiesByCountry")
    @WebResult(name = "string", targetNamespace = "http://www.webserviceX.NET", partName = "Body")
    public java.lang.String getCitiesByCountry(
        @WebParam(partName = "CountryName", name = "CountryName", targetNamespace = "http://www.webserviceX.NET")
        java.lang.String countryName
    );

    /**
     * Get weather report for all major cities around the world.
     */
    @WebMethod(operationName = "GetWeather")
    @WebResult(name = "string", targetNamespace = "http://www.webserviceX.NET", partName = "Body")
    public java.lang.String getWeather(
        @WebParam(partName = "CityName", name = "CityName", targetNamespace = "http://www.webserviceX.NET")
        java.lang.String cityName,
        @WebParam(partName = "CountryName", name = "CountryName", targetNamespace = "http://www.webserviceX.NET")
        java.lang.String countryName
    );
}
