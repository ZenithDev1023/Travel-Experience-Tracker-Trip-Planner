package utils;

import model.Destination;

import enums.DurationCategory;
import enums.Priority;
import enums.Pace;
import enums.Season;

public class ValidationBucketList {

    public static void validateDestination(Destination destination) {
        if (destination == null) {
            throw new IllegalArgumentException("Destination cannot be null");
        }
    }

    public static void validatePriority(Priority priority) {
        if (priority == null) {
            throw new IllegalArgumentException("Priority cannot be null");
        }
    }

    public static void validateNotes(String notes) {
        if (notes == null || notes.trim().isEmpty()) {
            throw new IllegalArgumentException("Notes cannot be null or empty");
        }

    }

    public static void validateTargetYear(int targetYear) {
        if (targetYear < 0) {
            throw new IllegalArgumentException("Target year cannot be negative");
        }
    }

    public static void validateInspirationSource(String inspirationSource) {
        if (inspirationSource == null || inspirationSource.trim().isEmpty()) {
            throw new IllegalArgumentException("Inspiration source cannot be null or empty");
        }
    }

    public static void validateDuration(DurationCategory duration) {

    }

    public static void validatePace(Pace pace) {
        if (pace == null) {
            throw new IllegalArgumentException("Pace cannot be null");
        }
    }

    public static void validateSeason(Season season) {
        if (season == null) {
            throw new IllegalArgumentException("Season cannot be null");
        }
    }
}