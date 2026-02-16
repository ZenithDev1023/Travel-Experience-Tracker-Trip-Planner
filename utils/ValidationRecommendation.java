package utils;

import model.Destination;

import enums.Season;

public class ValidationRecommendation {
    
    public static void validateDestination(Destination destination) {
        if (destination == null) {
            throw new IllegalArgumentException("Destination cannot be null");
        }
    }

    public static void validateMatchScore(int matchScore) {
        if (matchScore < 0) {
            throw new IllegalArgumentException("Match score cannot be negative");
        }
    }

    public static void validateReason(String reason) {
        if (reason == null || reason.trim().isBlank()) {
            throw new IllegalArgumentException("Reason cannot be null or empty");
        }
    }

    public static void validateSuggestedSeason(Season suggestedSeason) {
        if (suggestedSeason == null) {
            throw new IllegalArgumentException("Suggested season cannot be null");
        }
    }

    public static void validateEstimatedCost(double estimateCost) {
        if (estimateCost < 0.0) {
            throw new IllegalArgumentException("Estimated cost cannot be negative");
        }
    }

    public static void validateConfidenceLevel(int confidenceLevel) {
        if (confidenceLevel < 0) {
            throw new IllegalArgumentException("Confidence level cannot be negative");
        }
    }
}
