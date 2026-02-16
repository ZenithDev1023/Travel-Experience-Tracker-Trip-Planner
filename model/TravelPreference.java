package model;

import utils.ValidationTravelPreferences;
import model.Destination;
import model.TripLog;

import java.time.LocalDate;

import java.util.Set;
import java.util.HashSet;

import enums.BudgetLevel;
import enums.TravelStyle;
import enums.TripType;
import enums.Season;
import enums.Climate;
import enums.Rating;



public class TravelPreference {
    // Fields
    private Set<TripType> preferredTripTypes;
    private Set<Season> preferredSeasons;
    private BudgetLevel budgetRange;
    private TravelStyle travelStyle;
    private Set<Climate> climatePreferences;
    private Set<String> dealBreakers;
    private LocalDate lastUpdated;


    // Constructor
    public TravelPreference(
        Set<TripType> preferredTripTypes,
        Set<Season> preferredSeasons,
        BudgetLevel budgetRange,
        TravelStyle travelStyle,
        Set<Climate> climatePreferences,
        Set<String> dealBreakers,
        LocalDate lastUpdated
    ) {
        ValidationTravelPreferences.validatePreferredTripTypes(preferredTripTypes);
        ValidationTravelPreferences.validatePreferredSeason(preferredSeasons);
        ValidationTravelPreferences.validateBudgetRange(budgetRange);
        ValidationTravelPreferences.validateTravelStyle(travelStyle);
        ValidationTravelPreferences.validateClimatePreferences(climatePreferences);
        ValidationTravelPreferences.validateDealBreakers(dealBreakers);
        ValidationTravelPreferences.validateLastUpdated(lastUpdated);

        this.preferredTripTypes = new HashSet<>(preferredTripTypes);
        this.preferredSeasons = new HashSet<>(preferredSeasons);
        this.budgetRange = budgetRange;
        this.travelStyle = travelStyle;
        this.climatePreferences = new HashSet<>(climatePreferences);
        this.dealBreakers = new HashSet<>(dealBreakers);
        this.lastUpdated = lastUpdated;
    }

    public TravelPreference () {}


    public boolean matchesDestination(Destination destination) {
        for (String dealBreaker : dealBreakers) {
            if (destination.getHighlights().contains(dealBreaker)) {
                return false;
            }
        }

        int matchScore = 0;

        if (climatePreferences.contains(destination.getClimate())) {
            matchScore += 30;
        }

        if (destination.getTypicalBudget().ordinal() <= budgetRange.ordinal()) {
            matchScore += 25;
        }

        return matchScore >= 40;
    }



    public int calculateCompatibility(Destination destination) {
        if (destination == null) {
            throw new IllegalArgumentException("Destination canot be null");
        }

        int totalScore = 0;

        if (climatePreferences.contains(destination.getClimate())) {
            totalScore += 30;
        } else {
            totalScore -= 10;
        }

        if (destination.getTypicalBudget().ordinal() <= budgetRange.ordinal()) {
            totalScore += 25;
        } else {
            totalScore -= 10;
        }

        for (String dealBreaker : dealBreakers) {
            if (destination.getHighlights().contains(dealBreaker.toLowerCase()) || destination.getName().toLowerCase().contains(dealBreaker.toLowerCase())) {
                return 0;
            }
        }

        return Math.max(0, Math.min(100, totalScore));
    }


    public void updateFromTrip(TripLog trip) {
        if (trip == null || trip.getDestination() == null) return;

        Destination dest = trip.getDestination();
        Rating rating = trip.getOverallRating();

        if (rating.ordinal() >= Rating.GREAT.ordinal()) {
            if (trip.getTravelStyle() != null) {
                preferredTripTypes.addAll(trip.getDestination().getSuitableTripTypes());
            }
            
            if (dest.getClimate() != null) {
                climatePreferences.add(trip.getDestination().getClimate());
            }

            if (trip.getLovedAspects() != null) {
                trip.getLovedAspects().forEach(aspect -> System.out.println("You loved: " + aspect));
            }
        }

        if (trip.getDislikedAspects() != null) {
            dealBreakers.addAll(trip.getDislikedAspects());
        }

        lastUpdated = LocalDate.now();
    }


    // Getters
    public Set<TripType> getPreferredTripType() { return preferredTripTypes; }
    public Set<Season> getPreferredSeasons() { return preferredSeasons; }
    public BudgetLevel getBudgetRange() { return budgetRange; }
    public TravelStyle getTravelStyle() { return travelStyle; }
    public Set<Climate> getClimatePreferences() { return climatePreferences; }
    public Set<String> getDealBreakers() { return dealBreakers; }
    public LocalDate getLastUpdated() { return lastUpdated; }
}
