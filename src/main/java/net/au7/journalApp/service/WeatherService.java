package net.au7.journalApp.service;

import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String apiKey = "0c9d97a1242719cef78916cc667c1e23";

    private static final String API = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    public String getWeather(String city){
        String finalAPI = API.replace("API_KEY", apiKey).replace("CITY", city);
        restTemplate.exchange(finalAPI , HttpMethod.GET, null, );
    }

}

