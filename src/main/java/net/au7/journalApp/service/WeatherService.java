package net.au7.journalApp.service;

import net.au7.journalApp.api.response.WeatherResponse;
import net.au7.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city){
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
        if (weatherResponse != null){
            return weatherResponse;
        }
        else{
            String finalAPI = appCache.APP_CACHE.get("weather_api").replace("<apiKey>", apiKey).replace("<city>", city);
            ResponseEntity<WeatherResponse> response =
                    restTemplate.exchange(finalAPI , HttpMethod.GET, null, WeatherResponse.class);  //deserialization converting json to pojo
            WeatherResponse body = response.getBody();
            if(body != null){
                redisService.set("weather_of_" + city, body, 60l);
            }
            return body;
        }
    }

}

