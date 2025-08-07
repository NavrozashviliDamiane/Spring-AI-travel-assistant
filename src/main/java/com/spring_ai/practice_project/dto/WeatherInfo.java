package com.spring_ai.practice_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherInfo {
    private String destination;
    private int temperature;
    private String conditions;
    private String description;
    private String season;
}
