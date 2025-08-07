package com.spring_ai.practice_project.controller;

import com.spring_ai.practice_project.dto.BestTimeToVisit;
import com.spring_ai.practice_project.dto.WeatherInfo;
import com.spring_ai.practice_project.dto.WeatherRequest;
import com.spring_ai.practice_project.tool.WeatherTools;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo/weather")
@Tag(name = "Weather Tool Demo", description = "Demonstrates Spring AI tool calling with weather tools")
public class WeatherToolDemoController {

    private final ChatClient chatClient;
    private final WeatherTools weatherTools;

    @Autowired
    public WeatherToolDemoController(ChatClient chatClient, WeatherTools weatherTools) {
        this.chatClient = chatClient;
        this.weatherTools = weatherTools;
    }

    @Operation(summary = "Get weather information for a destination",
            description = "Demonstrates tool calling by getting weather information for a destination")
    @GetMapping("/info")
    public String getWeatherInfo(@RequestParam String destination) {
        if (destination == null || destination.trim().isEmpty()) {
            return "Destination is required";
        }

        String systemPrompt = """
            You are a weather information assistant. Provide detailed weather information for {destination}.
            Use the weather tools to get current weather information and best time to visit information.
            Format your response in a clear, readable structure.
            """;

        String userPrompt = "What's the current weather and best time to visit " + destination + "?";

        return chatClient.prompt()
                .system(systemPrompt)
                .user(userPrompt)
                .toolNames("getCurrentWeather", "getBestTimeToVisit")
                .call()
                .content();
    }

    @Operation(summary = "Plan a weather-aware trip",
            description = "Demonstrates multiple tool calls by planning a trip with weather considerations")
    @GetMapping("/plan")
    public String planWeatherAwareTrip(
            @RequestParam String destination,
            @RequestParam(required = false) Integer days) {

        if (destination == null || destination.trim().isEmpty()) {
            return "Destination is required";
        }

        int tripDays = days != null ? days : 3;

        String systemPrompt = """
            You are a travel planning assistant that specializes in weather-aware trip planning.
            For %s, create a %d-day trip plan that takes into account:
            
            1. The current weather conditions
            2. The best time to visit
            3. Indoor and outdoor activities based on weather
            
            Use the weather tools to get current weather information and best time to visit information.
            Format your response as a day-by-day itinerary with weather considerations for each day.
            """.formatted(destination, tripDays);

        String userPrompt = "Please create a weather-aware trip plan for " + destination;

        return chatClient.prompt()
                .system(systemPrompt)
                .user(userPrompt)
                .toolNames("getCurrentWeather", "getBestTimeToVisit")
                .call()
                .content();
    }
    

    @GetMapping("/direct/weather")
    public WeatherInfo getDirectWeather(@RequestParam String destination) {
        return weatherTools.getCurrentWeather().apply(new WeatherRequest(destination));
    }

    @GetMapping("/direct/best-time")
    public BestTimeToVisit getDirectBestTime(@RequestParam String destination) {
        return weatherTools.getBestTimeToVisit().apply(new WeatherRequest(destination));
    }
}
