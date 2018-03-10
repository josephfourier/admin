package com.thinkjoy.web.rpc.service.impl;

import com.thinkjoy.web.rpc.api.WeatherService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.util.Date;

class WeatherServiceImpl implements WeatherService {

   // @Value("${TIANQI_DAILY_WEATHER_URL}")
    private String TIANQI_DAILY_WEATHER_URL = "https://api.seniverse.com/v3/weather/daily.json";

    //@Value("${TIANQI_API_SECRET_KEY}")
    private String TIANQI_API_SECRET_KEY = "5zxk8m7n9nn2n5wz";

    //@Value("${TIANQI_API_USER_ID}")
    private String TIANQI_API_USER_ID = "U339B91A58";


    private String generateSignature(String data, String key) throws SignatureException {
        String result;
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes("UTF-8"));
            result = new sun.misc.BASE64Encoder().encode(rawHmac);
        }
        catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
        return result;
    }

    @Override
    public String generateWeatherUrl (
            String location,
            String language,
            String unit,
            String start,
            String days
    )  throws SignatureException, UnsupportedEncodingException {
        String timestamp = String.valueOf(new Date().getTime());
        String params = "ts=" + timestamp + "&ttl=30&uid=" + TIANQI_API_USER_ID;
        String signature = URLEncoder.encode(generateSignature(params, TIANQI_API_SECRET_KEY), "UTF-8");
        return TIANQI_DAILY_WEATHER_URL + "?" + params + "&sig=" + signature + "&location=" + location + "&language=" + language + "&unit=" + unit + "&start=" + start + "&days=" + days;
    }

    @Override
    public String generateWeatherUrl() throws SignatureException, UnsupportedEncodingException {
        return generateWeatherUrl("ip", "zh-Hans", "c", "1", "1");
    }

    public String getWeather() throws Exception {
        String url = generateWeatherUrl();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(new HttpGet(url));
        String jsonResult = EntityUtils.toString(response.getEntity());
        response.close();
        return jsonResult;
    }

}
