package com.spring_ai.practice_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BestTimeToVisit {
    private String destination;
    private String bestTime;
    private String reason;
}
