package net.au7.journalApp.service;

import net.au7.journalApp.api.response.QuotesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuotesService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String API_KEY = "+n0AqD3hLN0Ig9Jh/XuMHw==95hOn31kp7cr0MI5";
    private static final String API_URL = "https://api.api-ninjas.com/v2/quotes?categories=success,wisdom";

    public QuotesResponse[] getQuotes() {

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", API_KEY);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<QuotesResponse[]> response = restTemplate.exchange(
                API_URL,
                HttpMethod.GET,
                entity,
                QuotesResponse[].class
        );

        return response.getBody();
    }
}
