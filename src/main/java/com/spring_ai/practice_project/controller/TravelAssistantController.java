package com.spring_ai.practice_project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/travel")
@Tag(name = "Travel Assistant", description = "API for retrieving travel and country information")
public class TravelAssistantController {

    private final ChatClient chatClient;

    @Autowired
    public TravelAssistantController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Operation(summary = "Get country information by capital city", 
               description = "Retrieves detailed information about a country based on its capital city name")
    @GetMapping("/country")
    public String getCountryInfo(@RequestParam String capital) {
        return chatClient.prompt()
                .user("Give me information about the country where the capital is " + capital)
                .toolNames("countryByCapital")
                .call()
                .content();
    }
}
