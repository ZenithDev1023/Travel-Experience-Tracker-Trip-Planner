package utils;

import enums.TripType;
import enums.BudgetLevel;
import enums.Season;
import enums.TravelStyle;
import enums.Climate;

import java.time.LocalDate;
import java.util.Set;


public class ValidationTravelPreferences {

    public static void validatePreferredTripTypes(Set<TripType> preferredTripTypes) {
        if (preferredTripTypes == null) {
            throw new IllegalArgumentException("Preferred trip types canno be null");
        }

        if (preferredTripTypes.size() <= 0) {
            throw new IllegalArgumentException("Preferred trip types must be positive");
        }
    }

    public static void validatePreferredSeason(Set<Season> preferredSeasons) {
        if (preferredSeasons == null) {
            throw new IllegalArgumentException("Preferred seasons cannot be null");
        }

        if (preferredSeasons.size() <= 0) {
            throw new IllegalArgumentException("Preferred seasons must be positive");
        }
    }

    public static void validateBudgetRange(BudgetLevel budgetRange) {
        if (budgetRange == null) {
            throw new IllegalArgumentException("Budget range cnanot be null");
        }
    }

    public static void validateTravelStyle(TravelStyle travelStyle) {
        if (travelStyle == null) {
            throw new IllegalArgumentException("Travel style cannot be null");
        }
    }

    public static void validateClimatePreferences(Set<Climate> climatePreferences) {
        if (climatePreferences == null) {
            throw new IllegalArgumentException("Climate preferences cannot be null");
        }

        if (climatePreferences.size() <= 0) {
            throw new IllegalArgumentException("Climate preferences must be positive");
        }
    }

    public static void validateDealBreakers(Set<String> dealBreakers) {
        if (dealBreakers == null) {
            throw new IllegalArgumentException("Deal breakers cannot be null");
        }
    }

    public static void validateLastUpdated(LocalDate lastUpdated) {
        if (lastUpdated == null) {
            throw new IllegalArgumentException("Last updated cannot be null");
        }
        
        if (lastUpdated.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Last updated cannot be in the future");
        }
    }
}
