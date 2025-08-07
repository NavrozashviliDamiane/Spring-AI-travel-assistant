package com.spring_ai.practice_project.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class TravelPlannerService {

    private final ChatClient chatClient;

    public TravelPlannerService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String getTravelRecommendations(String destination, String interests, Integer days, String budget) {
        if (destination == null || destination.trim().isEmpty()) {
            return "Destination is required";
        }
        
        String systemPrompt = """
            You are a helpful travel assistant. Provide detailed travel recommendations for {destination}.
            Focus on the following aspects:
            - Best time to visit
            - Top attractions and activities
            - Local cuisine recommendations
            - Transportation options
            - Safety tips
            
            If specific interests are provided, tailor recommendations to those interests: {interests}.
            If trip duration is provided, create an itinerary for {days} days.
            If budget is specified, provide recommendations suitable for a {budget} budget.
            
            Use the country information tool if you need specific details about the country.
            """;
        
        String userPrompt = buildUserPrompt(destination, interests, days, budget);
        
        return chatClient.prompt()
                .system(systemPrompt)
                .user(userPrompt)
                .toolNames("countryByCapital")
                .call()
                .content();
    }

    public String generateItinerary(String destination, Integer days, String interests, 
                                   String accommodationType, String transportationType) {
        if (destination == null || destination.trim().isEmpty()) {
            return "Destination is required";
        }
        
        if (days == null || days < 1) {
            return "Valid number of days is required";
        }
        
        String systemPrompt = """
            You are a professional travel planner. Create a detailed day-by-day itinerary for a %d-day trip to %s.
            For each day, include:
            - Morning activities
            - Afternoon activities
            - Evening activities/dining recommendations
            - Estimated costs where possible
            
            Tailor the itinerary to these interests: %s.
            Accommodation preference: %s.
            Transportation preference: %s.
            
            Format the itinerary in a clear, readable structure with day headers.
            Use the country information tool if you need specific details about the country.
            """.formatted(
                days,
                destination,
                interests != null ? interests : "general sightseeing",
                accommodationType != null ? accommodationType : "mid-range hotels",
                transportationType != null ? transportationType : "public transportation and walking"
            );
        
        String userPrompt = "Please create a " + days + "-day itinerary for " + destination;
        
        return chatClient.prompt()
                .system(systemPrompt)
                .user(userPrompt)
                .toolNames("countryByCapital")
                .call()
                .content();
    }

    private String buildUserPrompt(String destination, String interests, Integer days, String budget) {
        StringBuilder promptBuilder = new StringBuilder("I'm planning a trip to " + destination);
        
        if (interests != null) {
            promptBuilder.append(". I'm interested in ").append(interests);
        }
        if (days != null) {
            promptBuilder.append(". I'll be staying for ").append(days).append(" days");
        }
        if (budget != null) {
            promptBuilder.append(". My budget is ").append(budget);
        }
        promptBuilder.append(". What do you recommend?");
        
        return promptBuilder.toString();
    }
}
