package model;

import java.util.Set;
import java.util.HashSet;

import java.util.List;
import java.util.ArrayList;

import enums.Contient;
import enums.Season;
import enums.Climate;
import enums.TripType;
import enums.BudgetLevel;

import utils.ValidationDestination;


public class Destination {
    // Fields
    private String name;
    private String country;
    private Contient contient; // Enum
    private Climate climate; // Enum
    private Set<TripType> suitableTripTypes; // Set & enum
    private Set<String> highlights;
    private int idealDuration;
    private BudgetLevel typicalBudget; // Enum


    public Destination(String name) {
        this.name = name;
        this.suitableTripTypes = new HashSet<>();
        this.highlights = new HashSet<>();
    }

    public Destination(String name, BudgetLevel typicalBudget) {
        this.name = name;
        this.typicalBudget = typicalBudget;
    }

    // Constructor 
    public Destination(
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
        ValidationDestination.validateIdealDuration(idealDuration);
        ValidationDestination.validateTypicalBudget(typicalBudget);

        this.name = name;
        this.country = country;
        this.contient = contient;
        this.climate = climate;
        this.suitableTripTypes = new HashSet<>();
        this.highlights = new HashSet<>();
        this.idealDuration = idealDuration;
        this.typicalBudget = typicalBudget;
    }


    public List<SeasonRecommendation> calculateSeasonSuitability() {
        List<SeasonRecommendation> recommendations = new ArrayList<>();

        if (this.climate == Climate.TEMPERATE) {
            recommendations.add(new SeasonRecommendation(
                Season.SPRING,
                "Perfect mild weather for sightseeing",
                90
            ));
        }

        if (this.highlights.contains("cherry_blossoms")) {
            recommendations.add(new SeasonRecommendation(
                Season.SPRING,
                "Peak cherry blossom season",
                95
            ));
        }

        if (this.typicalBudget == BudgetLevel.BUDGET) {
            recommendations.add(new SeasonRecommendation(
                Season.SHOULDER,
                "Lower prices, fewer crowds",
                85
            ));
        }

        return recommendations;
    }


    public String getHighlightSummary() {
        if (highlights == null || highlights.isEmpty()) {
            return "No highlights specificed";
        }

        return String.join(", " + highlights);
    }


    public int estimateCost() {
        int baseCost = switch(this.typicalBudget) {
            case BACKPACKER ->  50;
            case BUDGET -> 100;
            case MODERATE -> 200;
            case LUXURY -> 500;
            case SPLURGE -> 1000;
            default -> 200;
        };

        return baseCost * idealDuration;
    }


    public boolean isFamilyFriendly() {
        boolean hasFamilyActivities = suitableTripTypes.contains(TripType.FAMILY) ||
        suitableTripTypes.contains(TripType.NATURE) ||
        suitableTripTypes.contains(TripType.RELAXATION);

        boolean hasAdultOnly = suitableTripTypes.contains(TripType.ADULT) ||
        suitableTripTypes.contains(TripType.LUXURY_SPA);

        return hasFamilyActivities && !hasAdultOnly;
    }




    // Getters
    public String getName() { return name; }
    public String getCountry() { return country; }
    public Contient getContient() { return contient; }
    public Climate getClimate() { return climate; }
    public Set<TripType> getSuitableTripTypes() { return suitableTripTypes; }
    public Set<String> getHighlights() { return highlights; }
    public int getIdealDuration() { return idealDuration; }
    public BudgetLevel getTypicalBudget() { return typicalBudget; }
    

    // Setters
    public void setName(String name) { this.name = name; }
    public void setCountry(String country) { this.country = country; }
    public void setContient(Contient contient) { this.contient = contient; }
    public void setClimate(Climate climate) { this.climate = climate; }
    public void setSuitableTripTypes(Set<TripType> suitableTripTypes) { this.suitableTripTypes = suitableTripTypes; }
    public void setHighlights(Set<String> highlights) { this.highlights = highlights; }
    public void setIdealDuration(int idealDuration) { this.idealDuration = idealDuration; }
    public void setTypicalBudget(BudgetLevel typicalBudget) { this.typicalBudget = typicalBudget; } 

    @Override
    public String toString() {
        return String.format(
            "Name: %s, Country: %s, Contient: %s, Climate: %s, Ideal-Duration: %d, BudgetLevel: %s", 
            name, country, contient, climate, idealDuration, typicalBudget
        );
    }
}
