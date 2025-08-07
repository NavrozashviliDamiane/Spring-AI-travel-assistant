package com.spring_ai.practice_project.dto;

import java.util.List;
import java.util.Map;

public record CountryInfo(
        String name,
        String region,
        String subregion,
        long population,
        String currency,
        String language,
        String flag,
        List<String> timezones
) {}
