package service;

import java.time.LocalDate;

import model.Destination;
import model.Recommendation;
import model.TravelPreference;
import model.TripLog;

import java.util.List;
import java.util.ArrayList;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashMap;

import enums.BudgetLevel;
import enums.Climate;
import enums.Contient;
import enums.Season;
import enums.TripType;

import utils.ValidationDestination;


public class DestinationMatcher {
    // Fields
    private List<Destination> allDestinations;
    private List<TripLog> travelHistory;

    private Map<String, Integer> scoringWeight;
    private int dealBreakerThreshold;
    private Map<String, List<Recommendation>> recommendationCache;


    public DestinationMatcher(List<TripLog> travelHistory) {
        this.allDestinations = (allDestinations != null) ? allDestinations : new ArrayList<>();
        this.travelHistory = (travelHistory != null) ? travelHistory : new ArrayList<>();
        this.scoringWeight = (scoringWeight != null) ? scoringWeight : new HashMap<>();
        this.recommendationCache = (recommendationCache != null) ? recommendationCache : new HashMap<>();
        this.dealBreakerThreshold = 0;
    }

   
    public Destination addDestination (
        String name,
        String country,
        Contient contient,
        Climate climate,
        Set<TripType> suitableTripTypes,
        Set<String> highlights,
        int idealDuration,
        BudgetLevel typicalBudget
    ) {
        
        ValidationDestination.validateName(name);
        ValidationDestination.validateCountry(country);
        ValidationDestination.validateContient(contient);
        ValidationDestination.validateClimate(climate);
        ValidationDestination.validateSuitableTripTypes(suitableTripTypes);
        ValidationDestination.validateIdealDuration(idealDuration);
        ValidationDestination.validateTypicalBudget(typicalBudget);

        if (allDestinations.stream()
            .anyMatch(d -> d.getName().equalsIgnoreCase(name))) {
        throw new IllegalArgumentException("Destination already added");
        }

        Destination destination = new Destination(
            name, 
            country, 
            contient, 
            climate, 
            suitableTripTypes, 
            highlights, 
            idealDuration, 
            typicalBudget
        );

        allDestinations.add(destination);
        return destination;
    }


    public void load(Destination[] loadDestinations) {
        if (loadDestinations == null) return;

        for (Destination destination : loadDestinations) {
            if (destination != null && 
                allDestinations.stream()
                    .noneMatch(d -> d.getName().equalsIgnoreCase(destination.getName()))
            ) {
                allDestinations.add(destination);
            }
        }
    }


    public List<Recommendation> findMatches(TravelPreference userPreference) {
        List<Recommendation> matches = new ArrayList<>();
    
        if (userPreference == null || allDestinations == null) {
            return matches;
        }
    
        for (Destination dest : allDestinations) {
            if (dest == null) continue;
            
            // Simple matching logic
            boolean climateMatch = userPreference.getClimatePreferences()
                .contains(dest.getClimate());
            boolean budgetMatch = dest.getTypicalBudget()
                .ordinal() <= userPreference.getBudgetRange().ordinal();
            
            if (climateMatch && budgetMatch) {
                int score = 80; // Placeholder score
                matches.add(new Recommendation(
                    dest,
                    score,
                    "Matches your climate and budget preferences",
                    LocalDate.now()
                ));
            }
        }
        
        // Simple sort
        matches.sort((a, b) -> Integer.compare(b.getMatchScore(), a.getMatchScore()));
        
        return matches;
    }



    // Matching logic
    public int calculateMatchScore(Destination destination, TravelPreference userPreference) {
        if (destination == null || userPreference == null) {
            throw new IllegalArgumentException("Destination or userPreference cannot be null");
        }

        int score = userPreference.calculateCompatibility(destination);

        if (score >= 100) {
            System.out.println("Perfectly matched");
        } else if (score >= 70) {
            System.out.println("Very good match");
        } else if (score >= 50) {
            System.out.println("Good match");
        } else {
            System.out.println("Not a good match");
        }

        return score;
    }


    public List<Destination> rankByMatchQuality(List<Destination> destinations) {
        if (destinations == null) {
            return new ArrayList<>();
        }

        List<Destination> ranked = new ArrayList<>(destinations) ;

        ranked.sort((d1, d2) -> {
            if (d1 == null || d1.getName() == null) return -1;
            if (d2 == null || d2.getName() == null) return 1;
            return d1.getName().compareTo(d2.getName());
        });

        return ranked;
    }




    // Recommendation Generation
    public List<Destination> generateTopRecommendations(int count, TravelPreference userPreference, Recommendation recommendation) {
        List<Destination> recommendations = new ArrayList<>();

        if (travelHistory == null || travelHistory.isEmpty()) {
            return new ArrayList<>();
        }

        for (Destination dest : allDestinations) {
            if (dest != null) {
                recommendations.add(dest);
            }
        }
        

        List<Destination> filtered = recommendations.stream()
            .filter(dest -> dest != null)
            .filter(dest -> userPreference == null || userPreference.getDealBreakers() == null)
            .filter(dest -> recommendation.getMatchScore() >= 50)
            .limit(count > 0 ? count : recommendations.size())
            .collect(Collectors.toList());
        
        return filtered;
    }


    public List<Destination> suggestBasedOnHistory(List<Destination> destinations, TravelPreference userPreference) {
        List<Destination> trips = new ArrayList<>();

        if (travelHistory == null || travelHistory.isEmpty()) {
            return new ArrayList<>();
        }

        for (Destination dest : destinations) {
            if (dest != null) {
                trips.add(dest);
            }
        }

        List<Destination> filtered = trips.stream()
            .filter(dest -> dest != null)
            .filter(dest -> userPreference == null || userPreference.getDealBreakers() == null || userPreference.getClimatePreferences().contains(dest.getClimate())) 
            .filter(dest -> userPreference.getBudgetRange() == null || dest.getTypicalBudget().ordinal() <= userPreference.getBudgetRange().ordinal())
            .filter(dest -> userPreference.getPreferredTripType() == null)
            .collect(Collectors.toList());

        return filtered;
    }


    public List<Recommendation> getSeasonalRecommendations(Season season) {
        List<Recommendation> seasonalRecommendations = new ArrayList<>();

        if (travelHistory == null || travelHistory.isEmpty()) {
            return new ArrayList<>();
        }

        for (Destination destination : allDestinations) {
            if (destination == null) continue;

            if (isGoodForSeason(destination, season)) {
                Recommendation rec = new Recommendation(
                    destination,
                    85,
                    "Perfect for " + season + " travel",
                    LocalDate.now()
                );
                seasonalRecommendations.add(rec);
                
            }
        }

        return seasonalRecommendations;
    }
    
    
    private boolean isGoodForSeason(Destination destination, Season season) {

        switch (season) {
            case SPRING: 
                return destination.getClimate() == Climate.TEMPERATE || 
                       destination.getClimate() == Climate.MEDITERRANEAN;
            
            case SUMMER:
                return destination.getClimate() == Climate.TROPICAL || 
                       destination.getClimate() == Climate.MEDITERRANEAN;
            
            case AUTUMN:
                return destination.getClimate() == Climate.TEMPERATE;
            
            case WINTER:
                return destination.getClimate() == Climate.ARCTIC || 
                       destination.getClimate() == Climate.TEMPERATE;

            default:
                return true;
        }
    }



    // Filtering & Validation
    public List<Destination> filterByDealBreakers(List<Destination> destinations, TravelPreference userPreference) {
        List<Destination> list = new ArrayList<>(destinations);

        if (travelHistory == null || travelHistory.isEmpty()) {
            return new ArrayList<>();
        }

        for (Destination dest : destinations) {
            if (dest != null) {
                list.add(dest);
            }
        }

        List<Destination> filtered = list.stream()
             .filter(dest -> dest == null)
             .filter(dest -> userPreference == null || userPreference.getDealBreakers() == null)
             .collect(Collectors.toList());

        return filtered;
    }


    public boolean validateBudgetCompatibility(Destination destination, BudgetLevel budget) {
        if (destination == null || destination.getTypicalBudget() == null || budget == null) {
            return false;
        }

        if (destination.getTypicalBudget().ordinal() > budget.ordinal()) {
            return false;
        }

        System.out.println("This trip is compatible with your budget.");
        return true;
    }


    public void clearCache() {
        recommendationCache.clear();
        System.out.println("Recommendation cache cleared successfully");
    }
    

    // Getters
    public List<Destination> getAllDestinations() { return allDestinations; }
    public Map<String, Integer> getScoringWeight() { return scoringWeight; }
    public int getDealBreakerThreshold() { return dealBreakerThreshold; }
    public Map<String, List<Recommendation>> getRecommendationCache() { return recommendationCache; }
    public List<TripLog> getTravelHistory() { return travelHistory; }
}
