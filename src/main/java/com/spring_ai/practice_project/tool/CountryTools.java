package com.spring_ai.practice_project.tool;

import com.spring_ai.practice_project.dto.CapitalRequest;
import com.spring_ai.practice_project.dto.CountryInfo;
import com.spring_ai.practice_project.service.CountryInfoService.CountryInfoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class CountryTools {

    private final CountryInfoService countryInfoService;

    public CountryTools(CountryInfoService service) {
        this.countryInfoService = service;
    }

    @Bean
    @Description("Get information about a country by capital city")
    public Function<CapitalRequest, CountryInfo> countryByCapital() {
        return request -> countryInfoService.getCountryInfo(request.getCapital());
    }
}
