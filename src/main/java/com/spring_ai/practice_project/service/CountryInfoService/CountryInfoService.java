package com.spring_ai.practice_project.service.CountryInfoService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring_ai.practice_project.dto.CountryInfo;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Map;

@Service
public class CountryInfoService {

    private final RestTemplate restTemplate = new RestTemplate();

    public CountryInfo getCountryInfo(String capital) {
        String url = "https://restcountries.com/v3.1/capital/" + capital;

        try {
            var response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Map<String, Object>>>() {}
            );

            List<Map<String, Object>> countries = response.getBody();

            if (countries == null || countries.isEmpty()) {
                throw new RuntimeException("No country found for capital: " + capital);
            }

            Map<String, Object> country = countries.get(0);
            Map<String, Object> nameMap = (Map<String, Object>) country.get("name");
            Map<String, Object> currencies = (Map<String, Object>) country.get("currencies");
            Map<String, Object> languages = (Map<String, Object>) country.get("languages");

            String currency = currencies.keySet().stream().findFirst().orElse("Unknown");
            String currencyName = ((Map<String, String>) currencies.get(currency)).get("name");

            String language = languages.values().stream().findFirst().orElse("Unknown").toString();
            String flag = (String) ((Map<String, Object>) country.get("flags")).get("png");

            return new CountryInfo(
                    (String) nameMap.get("common"),
                    (String) country.get("region"),
                    (String) country.get("subregion"),
                    Long.parseLong(country.get("population").toString()),
                    currencyName,
                    language,
                    flag,
                    (List<String>) country.get("timezones")
            );

        } catch (RestClientException e) {
            throw new RuntimeException("Error fetching country data: " + e.getMessage(), e);
        }
    }
}
