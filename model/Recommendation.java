package model;

import utils.ValidationRecommendation;
import model.Destination;

import enums.Season;

import java.time.LocalDate;

import enums.BudgetLevel;
import enums.Climate;

public class Recommendation {
    // Fields
    private Destination destination; // Class
    private int matchScore;
    private String reason;
    private Season suggestedSeason; // Enum
    private double estimatedCost;
    private int confidenceLevel;
    private BudgetLevel budgetRange;


    // Constructor
    public Recommendation(
        Destination destination,
        int matchScore,
        String reason,
        Season suggestSeason,
        double estimateCost,
        int confidenceLevel,
        BudgetLevel budgetRange
    ) {
        ValidationRecommendation.validateDestination(destination);
        ValidationRecommendation.validateMatchScore(matchScore);
        ValidationRecommendation.validateReason(reason);
        ValidationRecommendation.validateSuggestedSeason(suggestSeason);
        ValidationRecommendation.validateEstimatedCost(estimateCost);
        ValidationRecommendation.validateConfidenceLevel(confidenceLevel);

        this.destination = destination;
        this.matchScore = matchScore;
        this.reason = reason;
        this.suggestedSeason = suggestSeason;
        this.estimatedCost = estimateCost;
        this.confidenceLevel = confidenceLevel;
        this.budgetRange = budgetRange;
    }


    public Recommendation(Destination destination2, int i, String string, LocalDate now) {}


    public boolean isWithinBudget(Destination destination) {
        if (destination == null || destination.getName() == null) {
            throw new IllegalArgumentException("Destination cannot be null");
        }
        if (destination.getTypicalBudget().ordinal() <= budgetRange.ordinal()) {
            return true;
        }

        return false;
    }


    public String getPlanningTips(Destination destination) {
        if (destination == null || destination.getName() == null) {
            throw new IllegalArgumentException("Destination cannot be null");
        }

        StringBuilder tips = new StringBuilder();
        tips.append("Planning Tips for ").append(destination.getName()).append("\n");

        tips.append("Budget: ").append(getBudgetTip(destination.getTypicalBudget())).append("\n");

        tips.append("Climate: ").append(getClimateTip(destination.getClimate())).append("\n");

        if (destination.getIdealDuration() > 0) {
            tips.append("Recommended stay: ").append(destination.getIdealDuration()).append(" days\n");
        }

        return tips.toString();
    }


    private String getBudgetTip(BudgetLevel budget) {
        return switch(budget) {
            case BACKPACKER -> "Hostels, street food, public transport";
            case BUDGET -> "Budget hotels, local restaurants";
            case MODERATE -> "Comfortable hotels, mix of dining options";
            case LUXURY -> "High-end hotels, fine dining, private tours";
            case SPLURGE -> "Luxury resorts, exclusive experiences";
            default -> "Various options available";
        };
    }

    private String getClimateTip(Climate climate) {
        return switch(climate) {
            case TROPICAL -> "🌴 Hot & humid year-round. Pack light clothes, sunscreen, insect repellent. "
                           + "Rain showers are common, especially in afternoon.";
            
            case TEMPERATE -> "🍂 Four distinct seasons. Pack layers. Spring/Autumn are mild and pleasant. "
                            + "Summer can be warm, winter cool with possible rain/snow.";
            
            case ARCTIC -> "❄️ Extremely cold. Thermal layers, winter jacket, gloves, hat essential. "
                          + "Limited daylight in winter, midnight sun in summer.";
            
            case DESERT -> "🏜️ Hot days, cold nights. Sun protection crucial. Light, breathable clothing "
                          + "for day, warm layer for evening. Stay hydrated!";
            
            case MEDITERRANEAN -> "☀️ Mild, wet winters & hot, dry summers. Perfect beach weather in summer. "
                                + "Spring/Autumn ideal for sightseeing.";
            
            case CONTINENTAL -> "🌡️ Large temperature variations between seasons. Hot summers, cold winters. "
                              + "Be prepared for weather changes even within a day.";
            
            case MOUNTAIN -> "🏔️ Temperature drops with altitude. Pack layers regardless of season. "
                           + "Sun protection important at high elevations.";
            
            case HUMID_SUBTROPICAL -> "🌧️ Hot, humid summers & mild winters. Summer has heavy rainfall. "
                                    + "Air conditioning is common indoors.";
            
            default -> "Check local weather forecasts before packing.";
        };
    }


    public String toDisplayString() {
        return String.format(
            "%s, %d, %s, %s, %d, %.2f, %d, %s",
            destination, matchScore, reason, suggestedSeason, estimatedCost, confidenceLevel, budgetRange
        );
    }



    // Getters
    public Destination getDestination() { return destination; }
    public int getMatchScore() { return matchScore; }
    public String getReason() { return reason; }
    public Season getSuggestedSeason() { return suggestedSeason; }
    public double getEstimatedCost() { return estimatedCost; }
    public int getConfidenceLevel() { return confidenceLevel; }
}
