package net.au7.journalApp.service;

import net.au7.journalApp.api.response.QuotesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuotesService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${quotes.api.key}")
    private String apiKey;
    private static final String API_URL = "https://api.api-ninjas.com/v2/quotes?categories=success,wisdom";

    public QuotesResponse[] getQuotes() {

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);

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
