package com.thinkjoy.web.rpc.api;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;

public interface WeatherService {
    String generateWeatherUrl(String location,
                              String language,
                              String unit,
                              String start,
                              String days)
            throws SignatureException, UnsupportedEncodingException;

    String generateWeatherUrl()
            throws SignatureException, UnsupportedEncodingException;

    String getWeather() throws Exception;
}
