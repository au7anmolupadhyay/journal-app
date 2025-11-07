package net.au7.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherResponse{
    private Current current;

    @Getter
    @Setter
    public class Current{

        private int temperature;

        @JsonProperty("wind_speed")
        private int windSpeed;

        @JsonProperty("weather_descriptions")
        public List<String> weather_descriptions;

        private int humidity;

        private int feelslike;

        private String isDay;
    }


}

