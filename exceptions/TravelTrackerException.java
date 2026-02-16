package exceptions;

public class TravelTrackerException extends RuntimeException {
    public TravelTrackerException(String message) {
        super(message);
    }

    public TravelTrackerException(String message, Throwable cause) {
        super(message, cause);
    }
}
