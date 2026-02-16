package exceptions;

public class DestinationNotFound extends TravelTrackerException {
    public DestinationNotFound(String message) {
        super(message);
    }

    public DestinationNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
