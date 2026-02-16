package exceptions;

public class ImportException extends TravelTrackerException {
    public ImportException(String message) {
        super(message);
    }

    public ImportException(String message, Throwable cause) {
        super(message, cause);
    }
}
