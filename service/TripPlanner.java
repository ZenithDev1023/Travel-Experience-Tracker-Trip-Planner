package service;

import java.io.StringWriter;
import java.time.LocalDate;

import java.util.List;
import java.util.ArrayList;

import java.util.Map;
import java.util.HashMap;

import model.TravelPreference;

import java.util.ArrayList;

import java.util.Set;
import java.util.HashSet;

import model.Destination;

import model.TripLog;

import enums.BudgetLevel;
import enums.Climate;
import enums.Contient;
import enums.TripType;



public class TripPlanner {
    private List<Destination> availableDestinations;
    private TravelPreference userPreference;
    private List<TripLog> travelHistory;

    public TripPlanner(
        List<Destination> availableDestinations,
        TravelPreference userPreference,
        List<TripLog> travelHistory
    ) {
        this.availableDestinations = new ArrayList<>(availableDestinations);
        this.userPreference = userPreference;
        this.travelHistory = new ArrayList<>(travelHistory);
    }


    public Map<String, Object> createTripPlan(Destination destination, LocalDate startDate, int days) {
        if (destination == null || startDate == null || days <= 0) {
            throw new IllegalArgumentException("Invalid trip parameters");
        }

        Map<String, Object> plan = new HashMap<>();
        plan.put("destination", destination);
        plan.put("start-date", startDate);
        plan.put("duration", days);
        plan.put("endDate", startDate.plusDays(days));
        plan.put("estimatedCost", calculateCost(destination, days));

        return plan;
    }


    private Destination ensureFullDestination(Destination dest) {
        if (dest.getCountry() == null) dest.setCountry("Unknown Country"); 
        if (dest.getContient() == null) dest.setContient(Contient.ASIA); 
        if (dest.getClimate() == null) dest.setClimate(Climate.CONTINENTAL); 
        if (dest.getIdealDuration() == 0) dest.setIdealDuration(0); 
        if (dest.getTypicalBudget() == null) dest.setTypicalBudget(BudgetLevel.MODERATE); 
        if (dest.getSuitableTripTypes() == null || dest.getSuitableTripTypes().isEmpty()) {
            dest.setSuitableTripTypes(Set.of(TripType.ADVENTURE));
        }

        if (dest.getHighlights() == null || dest.getHighlights().isEmpty()) {
            dest.setHighlights(Set.of("Sightseeing"));
        }
        return dest;
    }
        
        
    private double calculateCost(Destination destination, int days) {
        if (destination == null) return 0;

        double dailyCost;

        switch (destination.getTypicalBudget()) {
            case BudgetLevel.BACKPACKER -> dailyCost = 50;
            case BudgetLevel.BUDGET -> dailyCost = 100;
            case BudgetLevel.MODERATE -> dailyCost = 200;
            case BudgetLevel.LUXURY -> dailyCost = 500;
            case BudgetLevel.SPLURGE -> dailyCost = 1000;

            default -> dailyCost = 200;
        }

        return dailyCost * days;
    }


        
    public boolean checkDateConflicts(LocalDate startDate, int days) {
        for (TripLog trip : travelHistory) {
            if (startDate == trip.getStartDate()) {
                System.out.println("You have " + trip + " in the same period");
                return false;
            }
        }

        System.out.println("No current conflicts");
        return true;
    }


    public int estimateTotalCost(Destination destination, int days) {
        if (destination == null) {
            throw new IllegalArgumentException("Destination is empty");
        }

        if (days <= 0) {
            throw new IllegalArgumentException("Days must be positive");
        }

        return destination.estimateCost() * days;
    }


    public List<String> suggestDailyActivities(Destination destination, int days) {
        List<String> activities = new ArrayList<>();

        if (destination == null) {
            return activities;
        }

        Set<String> highlights = destination.getHighlights();
        if (highlights != null) {
            activities.addAll(highlights);
        }

        return activities;
    }


    public List<Map<String, Object>> planMultiDestinationTrip(List<Destination> destinations, int totalDays, int cost) {
        
        List<Map<String, Object>> list = new ArrayList<>();

        if (destinations == null) {
            return list;
        }

        if (totalDays <= 0) {
            throw new IllegalArgumentException("Total days cannot be negative");
        }

        int days = totalDays / destinations.size();
        
        for (Destination dest : destinations) {
            
            Map<String, Object> stop = new HashMap<>();
            
            stop.put("Destination", dest);
            stop.put("Days", days);
            stop.put("Cost", cost);

            list.add(stop);
        }

        return list;
    }



    public Map<String, Integer> createBudgetBreakdown(Destination destination, int days) {
        Map<String, Integer> list = new HashMap<>();

        if (destination == null || days <= 0) {
            return list;
        }

        int dailyBudget = destination.estimateCost();
        int totalBudget = dailyBudget * days;

        int acommodation = (int)(totalBudget * 0.4);
        int food = (int)(totalBudget * 0.25);
        int activities = (int)(totalBudget * 0.2);
        int transport = (int)(totalBudget * 0.15);

        list.put("accomodation", acommodation);
        list.put("food", food);
        list.put("activities", activities);
        list.put("transport", transport);

        return list;
    }



    public boolean validateDestinationCombination(List<Destination> destinations) {
        if (destinations == null) {
            return false;
        }

        List<Destination> combination = new ArrayList<>(destinations);

        combination.sort((d1, d2) -> {
            if (d1 == null || d1.getName() == null || 
                d2 == null || d2.getName() == null) {
                    return 0;
            }
            return d1.getContient().compareTo(d2.getContient());
        });

        return true;
    }


    // Getters
    public List<Destination> getAvailableDestinations() { return availableDestinations; }
    public TravelPreference getUserPreference() { return userPreference; }
    public List<TripLog> getTravelHistory() { return travelHistory; }
}
