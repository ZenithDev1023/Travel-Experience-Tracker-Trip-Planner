package service;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.Map;
import java.util.HashMap;

import model.TravelPreference;
import model.TripLog;

import enums.Rating;

public class PreferenceUpdater {
    private TravelPreference currentPreferences;
    private List<TripLog> userTravelHistory;
    private Map<LocalDate, TravelPreference> preferenceHistory;
    private double learningRate;


    public PreferenceUpdater(
        TravelPreference currentPreferences,
        List<TripLog> userTravelHistory,
        Map<LocalDate, TravelPreference> preferenceHistory,
        double learningRate
    ) {
        this.currentPreferences = currentPreferences;
        this.userTravelHistory = new ArrayList<>(userTravelHistory);
        this.preferenceHistory = new HashMap<>(preferenceHistory);
        this.learningRate = learningRate;
    }


    public TravelPreference updateFromTrip(TripLog trip) {
        if (trip == null) {
            return currentPreferences;
        }

        if (trip.getOverallRating().ordinal() >= Rating.GOOD.ordinal()) {
            currentPreferences.getPreferredTripType().addAll(trip.getDestination().getSuitableTripTypes());

            currentPreferences.getClimatePreferences().add(trip.getDestination().getClimate());
        }

        if (trip.getDislikedAspects() != null) {
            currentPreferences.getDealBreakers().addAll(trip.getDislikedAspects());
        }

        return currentPreferences;
    }


    public void learnFromRating(TripLog trip) {
        if (trip == null) {
            System.out.println("No trips");
        }

        int rating = trip.getOverallRating().ordinal();
        if (rating >= Rating.OKAY.ordinal()) {
            currentPreferences.getPreferredTripType().addAll(trip.getDestination().getSuitableTripTypes());
            currentPreferences.getClimatePreferences().add(trip.getDestination().getClimate());
        }
    }

    public void learnFromDislikes(TripLog trip) {
        if (trip == null) {
            System.out.println("No trips");
        }

        if (trip.getDislikedAspects() != null) {
            currentPreferences.getDealBreakers().addAll(trip.getDislikedAspects());
        }
    }


    public TravelPreference updateFromAllTrips() {
        if (userTravelHistory == null || userTravelHistory.isEmpty()) {
            return currentPreferences;
        }

        for (TripLog trip : userTravelHistory) {
            if (trip != null || trip.getDestination() != null) {
                if (trip.getOverallRating().ordinal() >= Rating.GOOD.ordinal()) {
                    currentPreferences.getPreferredTripType().addAll(trip.getDestination().getSuitableTripTypes());
                    currentPreferences.getClimatePreferences().add(trip.getDestination().getClimate());

                    if (trip.getDislikedAspects() != null) {
                        currentPreferences.getDealBreakers().addAll(trip.getDislikedAspects());
                    }
                }
            }
        }

        return currentPreferences;
    }


    public TravelPreference refreshPreferences() {
        TravelPreference newPrefs = new TravelPreference();

        for (TripLog trip : userTravelHistory) {
            if (trip == null) continue;

            if (trip.getOverallRating().ordinal() >= Rating.GOOD.ordinal()) {
                newPrefs.getPreferredTripType().addAll(trip.getDestination().getSuitableTripTypes());
                newPrefs.getClimatePreferences().add(trip.getDestination().getClimate());

                if (trip.getDislikedAspects() != null) {
                    newPrefs.getDealBreakers().addAll(trip.getDislikedAspects());
                }
            }
        }

        return newPrefs;
    }



    


    // Getters
    public TravelPreference getCurrentPreferences() { return currentPreferences; }
    public List<TripLog> getUserTravelHistory() { return userTravelHistory; }
    public Map<LocalDate, TravelPreference> getPreferenceHistory() { return preferenceHistory; }
    public double getLearningRate() { return learningRate; }


    // Setters
    public void savePreferenceSnapshot(TravelPreference currentPreference) { this.currentPreferences = currentPreference; }
    public void setCurrentPreferences(TravelPreference currentPreferences) { this.currentPreferences = currentPreferences; }
}
