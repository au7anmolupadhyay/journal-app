package net.au7.journalApp.service;

import net.au7.journalApp.api.response.QuotesResponse;
import net.au7.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuotesService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Value("${quotes.api.key}")
        private String apiKey;

    public QuotesResponse[] getQuotes() {

        String API_URL = appCache.APP_CACHE.get("quotes_api");

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
