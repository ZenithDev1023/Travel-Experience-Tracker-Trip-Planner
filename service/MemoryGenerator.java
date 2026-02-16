package service;

import java.util.List;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

import enums.BudgetLevel;

import java.util.HashSet;

import model.TripLog;

public class MemoryGenerator {
    private List<TripLog> travelHistory;
    private List<String> summaryTemplates;
    private Map<String, String> emojiMap;

    

    public MemoryGenerator(
        List<TripLog> travelHistory,
        List<String> summaryTemplates,
        Map<String, String> emojiMap
    ) {
        this.travelHistory = new ArrayList<>(travelHistory);
        this.summaryTemplates = new ArrayList<>(summaryTemplates);
        this.emojiMap = new HashMap<>(emojiMap);
    }


    public String generateTripSummary(TripLog triplog) {
        StringBuilder builder = new StringBuilder();

        builder.append("\n=== Trip Summary");
        builder.append("Trip Destination: " + triplog.getDestination() + "\n");
        builder.append("Trip Period: " + triplog.getStartDate() + " - " + triplog.getEndDate() + "\n");
        builder.append("Trip Companions: " + triplog.getTravelCompanions() + "\n");
        builder.append("Trip Style: " + triplog.getTravelStyle() + "\n");
        builder.append("Loved Aspects: " + triplog.getLovedAspects() + "\n");
        builder.append("Disliked Aspects: " + triplog.getDislikedAspects() +  "\n");
        builder.append("Best Memory: " + triplog.getBestMemory() + "\n");
        builder.append("Overall Rating: " + triplog.getOverallRating() + "\n");

        String summary = builder.toString();

        return summary;
    }


    public String createTravelTimeline() {
        StringBuilder builder = new StringBuilder();

        if (travelHistory == null || travelHistory.isEmpty()) {
            return "No travel history yet.";
        }

        List<TripLog> sortedTrips = new ArrayList<>(travelHistory);
        sortedTrips.sort(Comparator.comparing(TripLog::getStartDate));
        
        builder.append("Travel Timeline:\n");
        for (TripLog trip : sortedTrips) {
            if (trip != null && trip.getDestination() != null) {
                builder.append(". ")
                       .append(trip.getStartDate().getYear())
                       .append(": ")
                       .append(trip.getDestination().getName())
                       .append(" (")
                       .append(trip.getOverallRating())
                       .append(")\n");
            }
        }

        return builder.toString();
    }


    public List<String> extractBestMemories() {
        List<String> bestMemories = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        if (travelHistory == null || travelHistory.isEmpty()) {
            return new ArrayList<>();
        }

        List<TripLog> trips = new ArrayList<>(travelHistory);
        trips.sort(Comparator.comparing(TripLog::getBestMemory));

        builder.append("Best Memories:\n");
        for (TripLog trip : trips) {
            if (trip != null && trip.getDestination() != null) {
                builder.append(trip.getDestination() + " - " + trip.getBestMemory());
            }
        }

        String memories = builder.toString();
        bestMemories.add(memories);

        return bestMemories;
    }


    public Set<String> complieLovedAspects() {
        if (travelHistory == null || travelHistory.isEmpty()) {
            return new HashSet<>();
        }

        StringBuilder builder = new StringBuilder();

        List<TripLog> trips = new ArrayList<>(travelHistory);
        Set<String> aspects = new HashSet<>();

        builder.append("Loved Aspects:\n");
        for (TripLog trip : trips) {
            if (trip != null && trip.getDestination() != null) {
                builder.append(trip.getDestination() + " - " + trip.getLovedAspects());
            }
        }

        String lovedAspects = builder.toString();
        aspects.add(lovedAspects);

        return aspects;
    }



    public int countVisitedCountries() {
        if (travelHistory == null || travelHistory.isEmpty()) {
            return -1;
        }

        StringBuilder builder = new StringBuilder();
        List<TripLog> trips = new ArrayList<>(travelHistory);

        for (TripLog trip : trips) {
            if (trip != null && trip.getDestination() != null) {
                builder.append(trip.getDestination().getName());
            }
        }

        String b = builder.toString();

        Set<String> countries = new HashSet<>();
        countries.add(b);

        int countryCount = countries.size();

        return countryCount;
    }


    
    public List<String> findMostFrequentCompanions() {
        if (travelHistory == null || travelHistory.isEmpty()) {
            return new ArrayList<>();
        }

        StringBuilder builder = new StringBuilder();
        List<TripLog> trips = new ArrayList<>(travelHistory);

        for (TripLog trip : trips) {
            if (trip != null && trip.getDestination() != null) {
                builder.append(trip.getTravelCompanions());
            }
        }

        String b = builder.toString();

        List<String> companions = new ArrayList<>();
        companions.add(b);

        return companions;
    }



    public List<TripLog> identifyMilestoneTrips() {
        if (travelHistory == null || travelHistory.isEmpty()) {
            return new ArrayList<>();
        }

        List<TripLog> milestoneTrips = new ArrayList<>();

        for (TripLog trip : milestoneTrips) {
            if (trip != null && trip.getDestination() != null) {
                BudgetLevel budget = trip.getDestination().getTypicalBudget();
                if (budget == BudgetLevel.LUXURY || budget == BudgetLevel.SPLURGE) {
                    milestoneTrips.add(trip);
                }
            }
        }

        return milestoneTrips;   
    }



    public String formatForConsole(TripLog trip) {
        if (travelHistory == null || travelHistory.isEmpty()) {
            return "No travel history yet.";
        }

        StringBuilder builder = new StringBuilder();

        builder.append("=======================================");
        builder.append("🏝️ ").append(trip.getDestination().getName().toUpperCase()).append("\n");

        String dateRange = (trip.getStartDate() + " - " + trip.getEndDate());
        builder.append("📅 ").append(dateRange).append("\n");

        builder.append("⭐ ").append(trip.getOverallRating()).append("\n");
        
        if (trip.getTravelCompanions() != null && !trip.getTravelCompanions().isEmpty()) {
            builder.append("👥 With: ").append(String.join(", ", trip.getTravelCompanions())).append("\n");
        }

        if (trip.getLovedAspects() != null && !trip.getLovedAspects().isEmpty()) {
            builder.append("❤️ Loved").append(String.join(", ", trip.getLovedAspects())).append("\n");
        }

        builder.append("=======================================");

        return builder.toString();
    }



    public String createMemoryCard(TripLog trip) {
        if (trip == null) {
            return "⚠️ No trip data available";
        }

        StringBuilder builder = new StringBuilder();

        builder.append("==================================");

        if (trip.getBestMemory() != null && !trip.getBestMemory().isEmpty()) {
            builder.append("🌟 ").append(trip.getBestMemory()).append("\n\n");
        }

        if (trip.getLovedAspects() != null && !trip.getLovedAspects().isEmpty()) {
            builder.append("❤️ LOVED:\n");
            for (String loved : trip.getLovedAspects()) {
                builder.append(" • ").append(loved).append("\n");
            }
            builder.append("\n");
        }

        if (trip.getDislikedAspects() != null && !trip.getDislikedAspects().isEmpty()) {
            builder.append("⚠️ COULD IMPROVE:\n");
            for (String disliked : trip.getDislikedAspects()) {
                builder.append(" • ").append(disliked).append("\n");
            }
        }

        builder.append("==================================");

        return builder.toString();
    }



    public String generateYearInReview(int year) {
        if (travelHistory == null || travelHistory.isEmpty()) {
            return "No travel data";
        }

        List<TripLog> yearTrips = travelHistory.stream()
           .filter(trip -> trip != null && trip.getStartDate() != null)
           .filter(trip -> trip.getStartDate().getYear() == year)
           .collect(Collectors.toList());

        if (yearTrips.isEmpty()) {
            return "No trips in " + year;
        }

        int totalTrips = yearTrips.size();

        TripLog bestTrip = yearTrips.stream()
            .max(Comparator.comparing(t -> t.getOverallRating().ordinal()))
            .orElse(null);

        int totalDays = 0;
        for (TripLog trip : yearTrips) {
            totalDays += (int)ChronoUnit.DAYS.between(trip.getStartDate(), trip.getEndDate());
        }

        Set<String> countries = new HashSet<>();
        for (TripLog trip : yearTrips) {
            countries.add(trip.getDestination().getCountry());
        }

        int averageDays = totalDays / yearTrips.size();


        StringBuilder builder = new StringBuilder();

        builder.append("==========================\n");
        builder.append("   ").append(year).append("TRAVEL REVIEW\n");
        builder.append("==========================\n");

        builder.append("• ").append(totalTrips).append(" trips\n");
        builder.append("• ").append(bestTrip).append(" total trips\n");
        builder.append("• ").append(totalDays).append(" days\n");
        builder.append("• ").append(countries).append(" countries\n");
        builder.append("• ").append(averageDays).append(" days on average\n");

        builder.append("==========================\n");

        return builder.toString();
    }

    // Getters
    public List<TripLog> getTravelHistory() { return travelHistory; }
    public List<String> getSummaryTemplates() { return summaryTemplates; }
    public Map<String, String> getEmojiMap() { return emojiMap; }
}
