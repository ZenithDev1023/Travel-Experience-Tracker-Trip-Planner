package model;

import utils.ValidationTripLog;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import enums.TravelStyle;
import enums.Rating;

import java.util.List;
import java.util.ArrayList;

import java.util.Set;
import java.util.HashSet;

public class TripLog {
    // Fields
    private Destination destination; // Class
    private int idealDuration;

    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> travelCompanions;
    private TravelStyle travelStyle; // Enum
    private Rating overallRating;
    private Set<String> lovedAspects;
    private Set<String> dislikedAspects;
    private String bestMemory;


    // Constructor
    public TripLog(
        Destination destination,
        int idealDuration,
        LocalDate startDate,
        LocalDate endDate,
        List<String> travelCompanions,
        TravelStyle travelStyle,
        Rating overallRating,
        Set<String> lovedAspects,
        Set<String> dislikedAspects,
        String bestMemory
    ) {
        ValidationTripLog.validateDestination(destination);
        ValidationTripLog.validateStartDate(startDate);
        ValidationTripLog.validateEndDate(endDate, startDate);
        ValidationTripLog.validateTravelCompanions(travelCompanions);
        ValidationTripLog.validateTravelStyle(travelStyle);
        ValidationTripLog.validateRating(overallRating);
        ValidationTripLog.validateBestMemory(bestMemory);

        this.destination = destination;
        this.idealDuration = idealDuration;
        this.startDate = startDate;
        this.endDate = endDate;
        this.travelCompanions = new ArrayList<>();
        this.travelStyle = travelStyle;
        this.overallRating = overallRating;
        this.lovedAspects = new HashSet<>();
        this.dislikedAspects = new HashSet<>();
        this.bestMemory = bestMemory;
    }


    public int getDuration(Destination destination) {
        if (destination == null) {
            throw new IllegalArgumentException("Destination is null");
        }

        return destination.getIdealDuration();
    }


    public int caluclateDailyCost() {
        if (destination == null) {
            throw new IllegalArgumentException("No destination set.");
        }

        int totalCost = destination.estimateCost();

        if (idealDuration > 0) {
            return totalCost / idealDuration;
        }
        return totalCost;
    }
        
      
    public String generateMemorySummary() {
        StringBuilder summary = new StringBuilder();

        if (bestMemory != null || !bestMemory.trim().isEmpty()) {
            summary.append("Best Memory: ").append(bestMemory).append("\n");
        }

        if (lovedAspects != null || !lovedAspects.isEmpty()) {
            summary.append("Loved: ").append(lovedAspects).append("\n");
        }

        if (dislikedAspects != null || !dislikedAspects.isEmpty()) {
            summary.append("Disliked: ").append(dislikedAspects);
        }

        return summary.toString().trim();
    }


    public TripLog toTripRecord() {
        return new TripLog(
            destination,
            idealDuration,
            startDate,
            endDate,
            travelCompanions,
            travelStyle,
            overallRating,
            lovedAspects,
            dislikedAspects,
            bestMemory
        );
    }


    private int calculateDuration() {
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }


        
    // Getters
    public Destination getDestination() { return destination; }
    public int getIdealDuration() { return idealDuration; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public List<String> getTravelCompanions() { return travelCompanions; }
    public TravelStyle getTravelStyle() { return travelStyle; }
    public Rating getOverallRating() { return overallRating; }
    public Set<String> getLovedAspects() { return lovedAspects; }
    public Set<String> getDislikedAspects() { return dislikedAspects; }
    public String getBestMemory() { return bestMemory; }
}
