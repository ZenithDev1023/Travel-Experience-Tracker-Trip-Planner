package persistence;

import service.DestinationMatcher;

import persistence.DestinationCsvHandler;
import persistence.TripLogCsvHandler;

import model.Destination;

import java.util.List;

import model.TripLog;


public class CsvFileManager {
    private final DestinationMatcher destinationMatcher;
    private final List<TripLog> travelHistory;

    public CsvFileManager(
        DestinationMatcher destinationMatcher,
        List<TripLog> travelHistory
    ) {
        this.destinationMatcher = destinationMatcher;
        this.travelHistory = travelHistory;
    }

    public void loadAll() {
        Destination[] loadedDestinations = DestinationCsvHandler.loadDestinations();
        if (loadedDestinations != null) {
            destinationMatcher.load(loadedDestinations);
        }

        List<TripLog> loadedTrips = TripLogCsvHandler.loadTripLogs();
        if (loadedTrips != null) {
            travelHistory.addAll(loadedTrips);
        }
    }

    public void saveAll() {
        List<Destination> destinations = destinationMatcher.getAllDestinations();

        DestinationCsvHandler.saveDestinations(destinations);

        TripLogCsvHandler.saveTripLogs(travelHistory);
    }
    
}
