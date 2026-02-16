package service;

import utils.ValidationPatternAnalyzer;

import java.util.List;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;

import model.TripLog;
import model.Destination;
import model.TravelPreference;

import enums.TripType;
import enums.Rating;
import enums.Season;
import enums.Climate;
import enums.BudgetLevel;


public class PatternAnalyzer {
    // Fields
    private List<TripLog> travelHistory;
    private Set<Destination> destinations;
    private TravelPreference userPreferences;
    private Map<String, Object> analysisResults;


    // Constructor
    public PatternAnalyzer(
        List<TripLog> travelHistory,
        Set<Destination> destinations,
        TravelPreference userPreferences,
        Map<String, Object> analysisResults
    ) {
        ValidationPatternAnalyzer.validateTravelHistory(travelHistory);
        ValidationPatternAnalyzer.validateDestinations(destinations);
        ValidationPatternAnalyzer.validateUserPreferences(userPreferences);
        ValidationPatternAnalyzer.validateAnalysisResult(analysisResults);

        this.travelHistory = new ArrayList<>(travelHistory);
        this.destinations = new HashSet<>(destinations);
        this.userPreferences = userPreferences;
        this.analysisResults = new HashMap<>(analysisResults);
    }


    public Set<TripType> analyzeFavoriteTripType() {
        Set<TripType> favorites = new HashSet<>();

        if (travelHistory == null || travelHistory.isEmpty()) {
            return favorites;
        }

        for (TripLog trip : travelHistory) {
            if (trip == null || trip.getOverallRating() == null) {
                continue;
            }

            if (trip.getOverallRating().ordinal() >= Rating.GOOD.ordinal()) {
                Set<TripType> tripTypes = trip.getDestination().getSuitableTripTypes();

                if (tripTypes != null) {
                    favorites.addAll(tripTypes);
                }
            }
        }

        return favorites;
    }


    // Helper method
    private Season determineSeasonFromDate(LocalDate date) {
        if (date == null) return null;

        int month = date.getMonthValue();

        if (month >= 3 && month <= 5) return Season.SPRING;
        if (month >= 6 && month <= 8) return Season.SUMMER;
        if (month >= 9 && month <= 11) return Season.AUTUMN;
        return Season.WINTER;
    }


    public Map<Season, Integer> calculateSeasonPreferences() {
        Map<Season, Integer> seasons = new HashMap<>();

        if (travelHistory == null || travelHistory.isEmpty()) {
            return null;
        }

        for (TripLog trip : travelHistory) {
            if (trip.getStartDate() == null) continue;
            
            Season season = determineSeasonFromDate(trip.getStartDate());

            if (season != null) {
                seasons.put(season, seasons.getOrDefault(season, 0) + 1);
            }
        }

        return seasons;
    }


    public Set<Climate> findMostVisitedClimates() {
        Set<Climate> climates = new HashSet<>();
    
        if (travelHistory == null || travelHistory.isEmpty()) {
            return climates;
        }
    
        Map<Climate, Integer> climateCounts = new HashMap<>();
        int maxCount = 0;
    
        for (TripLog trip : travelHistory) {
            if (trip == null || trip.getDestination() == null) {
                continue;
            }
    
            Climate climate = trip.getDestination().getClimate();
            if (climate == null) {
                continue;
            }
            
            // CORRECT: Get current, calculate new, update map
            int currentCount = climateCounts.getOrDefault(climate, 0);
            int newCount = currentCount + 1;
            climateCounts.put(climate, newCount); // Just update the map
            
            // Update maxCount
            if (newCount > maxCount) {
                maxCount = newCount;
            }
        }
    
        // MISSING STEP: Find all climates with maxCount
        for (Map.Entry<Climate, Integer> entry : climateCounts.entrySet()) {
            if (entry.getValue() == maxCount) {
                climates.add(entry.getKey());
            }
        }
    
        return climates;
    }



    public int getAverageTripDuration() {
        if (travelHistory == null || travelHistory.isEmpty()) {
            return 0;
        }

        long totalDays = 0;
        int validTrips = 0;

        for (TripLog trip : travelHistory) {
            if (trip != null) {
                LocalDate start = trip.getStartDate();
                LocalDate end = trip.getEndDate();

                if (start != null && end != null) {
                    long daysBetween = ChronoUnit.DAYS.between(start, end);
                    totalDays += daysBetween;
                    validTrips++;
                }
            }
        }

        if (validTrips == 0) {
            return 0;
        }

        return (int) (totalDays / validTrips);
    }



    public BudgetLevel calculateBudgetPattern() {
        if (travelHistory == null || travelHistory.isEmpty()) {
            return BudgetLevel.MODERATE;
        }

        Map<BudgetLevel, Integer> budgetCounts = new HashMap<>();

        for (TripLog trip : travelHistory) {
            if (trip != null && trip.getDestination() != null) {
                BudgetLevel budget = trip.getDestination().getTypicalBudget();

                if (budget != null) {
                    int currentCount = budgetCounts.getOrDefault(budget, 0);
                    budgetCounts.put(budget, currentCount + 1);
                }
            }
        }

        if (budgetCounts.isEmpty()) {
            return null;
        }

        BudgetLevel mostCommon = null;
        int maxCount = 0;

        for (Map.Entry<BudgetLevel, Integer> entry : budgetCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostCommon = entry.getKey();
            }
        }

        return mostCommon;
    }



    // TRIP HISTORY ANALYSIS
    public List<TripLog> getHighestRatedTrips() {
        List<TripLog> highestRatedTrips = new ArrayList<>();

        if (travelHistory == null || travelHistory.isEmpty()) {
            return highestRatedTrips;
        }

        Rating maxRating = null;
        for (TripLog trip : travelHistory) {
            if (trip != null && trip.getOverallRating() != null) {
                if (maxRating == null || trip.getOverallRating().ordinal() > maxRating.ordinal()) {
                    maxRating = trip.getOverallRating();
                }
            }
        }

        if (maxRating == null) {
            return highestRatedTrips;
        }

        for (TripLog trip : travelHistory) {
            if (trip != null && 
                trip.getOverallRating() != null && 
                trip.getOverallRating() == maxRating) {
                highestRatedTrips.add(trip);
            }
        }
        
        return highestRatedTrips;
    }



    public Map<String, Integer> findTravelCompanionPatterns() {
        Map<String, Integer> companionCounts = new HashMap<>();

        if (travelHistory == null || travelHistory.isEmpty()) {
            return companionCounts;
        }
        
        for (TripLog trip : travelHistory) {
            if (trip != null && trip.getTravelCompanions() != null) {
                List<String> companions = trip.getTravelCompanions();

                for (String companion : companions) {
                    if (companion != null && !companion.trim().isEmpty()) {
                        int currentCount = companionCounts.getOrDefault(companion, 0);
                        companionCounts.put(companion, currentCount + 1);
                    }
                }
            }
        }

        return companionCounts;
    }



    public int calculateConsistencyScore() {
        if (travelHistory == null || travelHistory.isEmpty()) {
            return 0;
        }

        Map<Season, Integer> seasonCounts = new HashMap<>();

        int totalTrips = travelHistory.size();
        int maxTripsInOneSeason = Collections.max(seasonCounts.values());

        return (maxTripsInOneSeason * 100) / totalTrips;
    }



    public Set<String> identifyImprovementAreas() {
        Set<String> allDislikes = new HashSet<>();

        if (travelHistory == null || travelHistory.isEmpty()) {
            return allDislikes;
        }

        for (TripLog trip : travelHistory) {
            if (trip != null && trip.getDislikedAspects() != null) {
                for (String dislike : trip.getDislikedAspects()) {
                    if (dislike != null && !dislike.trim().isEmpty()) {
                        allDislikes.add(dislike);
                    }
                }
            }
         }
         
         return allDislikes;
    }



    // User Profile Creation
    public String generateTravelProfileSummary(TravelPreference userPreference) {
        StringBuilder builder = new StringBuilder();

        builder.append("\n=== User Profile ===");
        builder.append("\nPreferred Trip Type: ");
        builder.append(
            userPreference.getPreferredTripType()
                .stream()
                .map(TripType::name)
                .collect(Collectors.joining(", "))
        );

        builder.append("\nPreferred Season: ");
        builder.append(
            userPreference.getPreferredSeasons()
                .stream()
                .map(Season::name)
                .collect(Collectors.joining(", "))
        );

        builder.append("\nBudget range: ");
        builder.append(userPreference.getBudgetRange());

        builder.append("\nTravel Style: ");
        builder.append(userPreference.getTravelStyle());

        builder.append("\nClimate Preferences: ");
        builder.append(
            userPreference.getClimatePreferences()
                .stream()
                .map(Climate::name)
                .collect(Collectors.joining(", "))
        );

        builder.append("\nDeal-Breakers: ");
        builder.append(
            userPreference.getDealBreakers()
                .stream()
                .collect(Collectors.joining(", "))
        );

        builder.append("\nLast Updated: ");
        builder.append(userPreference.getLastUpdated());

        return builder.toString();
    }



    public int calculateMatchWithPreferences(Destination destination) {
        if (destination == null) {
            throw new IllegalArgumentException("Destination cannot be null");
        }

        int score = 0;
        TravelPreference pref = getUserPreference();

        for (TripType type : destination.getSuitableTripTypes()) {
            if (pref.getPreferredTripType().contains(type)) {
                score += 30;
            }
        }

        if (pref.getClimatePreferences().contains(destination.getClimate())) {
            score += 20;
        }

        if (pref.getBudgetRange() == destination.getTypicalBudget()) {
            score += 25;
        } 
        
        return score;
    }
    
    public boolean hasSufficientData() {
        if (travelHistory != null || !travelHistory.isEmpty()) {
            return true;
        }

        return false;
    }


    // Getters
    public List<TripLog> travelHistory() { return travelHistory; }
    public Set<Destination> getDestinations() { return destinations ;}
    public TravelPreference getUserPreference() { return userPreferences; }
    public Map<String, Object> getAnalysisResult() { return analysisResults; }
}
