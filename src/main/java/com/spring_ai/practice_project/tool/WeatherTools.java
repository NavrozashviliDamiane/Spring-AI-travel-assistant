package com.spring_ai.practice_project.tool;

import com.spring_ai.practice_project.dto.BestTimeToVisit;
import com.spring_ai.practice_project.dto.WeatherInfo;
import com.spring_ai.practice_project.dto.WeatherRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Component
public class WeatherTools {

    private final RestTemplate restTemplate;
    private final Map<String, WeatherInfo> weatherCache;
    private final Map<String, BestTimeToVisit> bestTimeCache;

    public WeatherTools() {
        this.restTemplate = new RestTemplate();
        this.weatherCache = initializeWeatherCache();
        this.bestTimeCache = initializeBestTimeCache();
    }

    private Map<String, WeatherInfo> initializeWeatherCache() {
        Map<String, WeatherInfo> cache = new HashMap<>();
        
        cache.put("paris", new WeatherInfo("Paris", 22, "Partly Cloudy", "Mild and pleasant", "Spring"));
        cache.put("tokyo", new WeatherInfo("Tokyo", 28, "Sunny", "Hot and humid", "Summer"));
        cache.put("new york", new WeatherInfo("New York", 15, "Rainy", "Cool and wet", "Fall"));
        cache.put("sydney", new WeatherInfo("Sydney", 30, "Sunny", "Hot and dry", "Summer"));
        cache.put("london", new WeatherInfo("London", 18, "Cloudy", "Mild with occasional rain", "Spring"));
        cache.put("dubai", new WeatherInfo("Dubai", 38, "Sunny", "Very hot and dry", "Summer"));
        cache.put("moscow", new WeatherInfo("Moscow", -5, "Snowy", "Cold and snowy", "Winter"));
        cache.put("rio de janeiro", new WeatherInfo("Rio de Janeiro", 32, "Sunny", "Hot and humid", "Summer"));
        cache.put("cape town", new WeatherInfo("Cape Town", 25, "Sunny", "Warm and dry", "Summer"));
        cache.put("bangkok", new WeatherInfo("Bangkok", 33, "Partly Cloudy", "Hot and humid", "Rainy Season"));
        
        return cache;
    }
    
    private Map<String, BestTimeToVisit> initializeBestTimeCache() {
        Map<String, BestTimeToVisit> cache = new HashMap<>();
        
        cache.put("paris", new BestTimeToVisit("Paris", "Spring (April to June) or Fall (September to October)", 
                "Mild temperatures and fewer tourists"));
        cache.put("tokyo", new BestTimeToVisit("Tokyo", "Spring (March to May) or Fall (September to November)", 
                "Cherry blossoms in spring, colorful foliage in fall, and mild temperatures"));
        cache.put("new york", new BestTimeToVisit("New York", "Late Spring (May to June) or Fall (September to November)", 
                "Mild temperatures, fewer tourists, and beautiful scenery"));
        cache.put("sydney", new BestTimeToVisit("Sydney", "Spring (September to November) or Fall (March to May)", 
                "Mild temperatures and fewer tourists"));
        cache.put("london", new BestTimeToVisit("London", "Late Spring to Early Fall (May to September)", 
                "Longer daylight hours, warmer temperatures, and outdoor events"));
                
        return cache;
    }

    @Bean("getCurrentWeather")
    @Description("Get current weather information for a travel destination")
    public Function<WeatherRequest, WeatherInfo> getCurrentWeather() {
        return request -> Optional.ofNullable(request)
                .map(WeatherRequest::getDestination)
                .map(destination -> destination.toLowerCase().trim())
                .map(normalizedDestination -> weatherCache.getOrDefault(
                    normalizedDestination,
                    new WeatherInfo(
                        request.getDestination(),
                        25, 
                        "Sunny", 
                        "Weather information not available for this destination", 
                        "Unknown"
                    )
                ))
                .orElse(new WeatherInfo(
                    "Unknown",
                    25, 
                    "Sunny", 
                    "No destination provided", 
                    "Unknown"
                ));
    }
    
    @Bean("getBestTimeToVisit")
    @Description("Get information about the best season to visit a destination")
    public Function<WeatherRequest, BestTimeToVisit> getBestTimeToVisit() {
        return request -> Optional.ofNullable(request)
                .map(WeatherRequest::getDestination)
                .map(destination -> destination.toLowerCase().trim())
                .map(normalizedDestination -> bestTimeCache.entrySet().stream()
                    .filter(entry -> normalizedDestination.contains(entry.getKey()))
                    .map(Map.Entry::getValue)
                    .findFirst()
                    .orElse(new BestTimeToVisit(
                        request.getDestination(),
                        "Spring or Fall",
                        "Generally, spring and fall offer the best balance of good weather and fewer tourists in most destinations"
                    ))
                )
                .orElse(new BestTimeToVisit(
                    "Unknown",
                    "Spring or Fall",
                    "No destination provided"
                ));
    }
}
