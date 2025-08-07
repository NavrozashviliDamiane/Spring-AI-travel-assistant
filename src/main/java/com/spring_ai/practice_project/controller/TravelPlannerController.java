package com.spring_ai.practice_project.controller;

import com.spring_ai.practice_project.service.TravelPlannerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/travel/planner")
@Tag(name = "Travel Planner", description = "API for planning travel with AI assistance")
public class TravelPlannerController {

    private final TravelPlannerService travelPlannerService;

    @Autowired
    public TravelPlannerController(TravelPlannerService travelPlannerService) {
        this.travelPlannerService = travelPlannerService;
    }

    @Operation(summary = "Get travel recommendations for a destination", 
               description = "Provides travel recommendations based on destination and preferences")
    @GetMapping("/recommendations")
    public String getTravelRecommendations(
            @RequestParam String destination,
            @RequestParam(required = false) String interests,
            @RequestParam(required = false) Integer days,
            @RequestParam(required = false) String budget) {
        
        return travelPlannerService.getTravelRecommendations(destination, interests, days, budget);
    }
    
    @Operation(summary = "Generate a custom travel itinerary", 
               description = "Creates a day-by-day itinerary based on destination and preferences")
    @PostMapping("/itinerary")
    public String generateItinerary(
            @RequestParam String destination,
            @RequestParam Integer days,
            @RequestParam(required = false) String interests,
            @RequestParam(required = false) String accommodationType,
            @RequestParam(required = false) String transportationType) {
        
        return travelPlannerService.generateItinerary(destination, days, interests, accommodationType, transportationType);
    }
}
