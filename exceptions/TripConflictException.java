package exceptions;

public class TripConflictException extends TravelTrackerException {
    public TripConflictException(String message) {
        super(message);
    }

    public TripConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
