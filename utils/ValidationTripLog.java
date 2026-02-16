package utils;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import enums.TravelStyle;
import enums.Rating;

import model.Destination;



public class ValidationTripLog {
    
    public static void validateDestination(Destination destination) {
        if (destination == null) {
            throw new IllegalArgumentException("Destination cannot be null");
        }
    }

    public static void validateStartDate(LocalDate startDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("Start date cannot be null");
        }
        
        if (startDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Start date be in the future");
        }
    }

    public static void validateEndDate(LocalDate endDate, LocalDate startDate) {
        if (endDate == null) {
            throw new IllegalArgumentException("End date cannot be null");
        }

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before the start date");
        }
    }

    public static void validateTravelCompanions(List<String> travelCompanions) {
        if (travelCompanions == null) {
            throw new IllegalArgumentException("Travel companions cannot be null");
        }
    }

    public static void validateTravelStyle(TravelStyle travelStyle) {
        if (travelStyle == null) {
            throw new IllegalArgumentException("Travel style cannot be null");
        }
    }

    public static void validateRating(Rating overallRating) {
        if (overallRating == null) {
            throw new IllegalArgumentException("Rating cannot be null");
        }

    }


    public static void validateBestMemory(String bestMemory) {
        if (bestMemory == null || bestMemory.trim().isEmpty()) {
            throw new IllegalArgumentException("Best memory cannot be null or empty");
        }
    }
}
