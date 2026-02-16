package utils;

import enums.Contient;
import enums.Climate;
import enums.TripType;
import enums.BudgetLevel;


import java.util.Set;

public class ValidationDestination {

    public static void validateName(String name) {
        if (name == null || name.trim().isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
    }

    public static void validateCountry(String country) {
        if (country == null || country.trim().isBlank()) {
            throw new IllegalArgumentException("Country cannot be null or empty");
        }

        if (country.length() < 2) {
            throw new IllegalArgumentException("Country must be atleast 2 characters long");
        }

    }

    public static void validateContient(Contient contient) {
        if (contient == null) {
            throw new IllegalArgumentException("Contient cannot be null");
        }
    }

    public static void validateClimate(Climate climate) {
        if (climate == null) {
            throw new IllegalArgumentException("Climate cannot be null");
        }
    }

    public static void validateIdealDuration(int idealDuration) {
        if (idealDuration <= 0) {
            throw new IllegalArgumentException("Ideal duration cannot be negative or zero");
        }

        if (idealDuration >= 180) {
            throw new IllegalArgumentException("Ideal duration cannot be more than 6 months");
        }
    }

    public static void validateTypicalBudget(BudgetLevel typicalBudget) {
        if (typicalBudget == null) {
            throw new IllegalArgumentException("Typical argument cannot be null");
        }
    }

    public static void validateSuitableTripTypes(Set<TripType> suitableTripTypes) {
        if (suitableTripTypes == null || suitableTripTypes.isEmpty()) {
            throw new IllegalArgumentException("Suitable trip types is empty");
        }
    }

}
