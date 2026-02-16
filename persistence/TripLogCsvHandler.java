package persistence;
import java.io.BufferedWriter;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;

import model.Destination;
import model.TripLog;

import enums.TravelStyle;
import enums.Rating;
import enums.TripType;
import enums.Contient;
import enums.BudgetLevel;
import enums.Climate;

public class TripLogCsvHandler {
    private static final String FILE_NAME = "data/triplogs.csv";

    public static void saveTripLogs(List<TripLog> logs) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (TripLog trip : logs) {
                
                String tripTypes = trip.getDestination().getSuitableTripTypes() == null
                    ? ""
                    : String.join(";",
                        trip.getDestination().getSuitableTripTypes()
                            .stream()
                            .map(Enum::name)
                            .toList()
                    );

                String highlights = trip.getDestination().getHighlights() == null
                    ? ""
                    : String.join(";", trip.getDestination().getHighlights());

                String companions = trip.getTravelCompanions() == null
                    ? ""
                    : String.join(";", trip.getTravelCompanions());

                String loved = trip.getLovedAspects() == null
                    ? ""
                    : String.join(";", trip.getLovedAspects());

                String disliked = trip.getDislikedAspects() == null
                    ? ""
                    : String.join(";", trip.getDislikedAspects());

                String line = 
                    trip.getDestination().getName() + "," +
                    trip.getDestination().getCountry() + "," +
                    trip.getDestination().getContient() + "," +
                    trip.getDestination().getClimate() + "," +
                    tripTypes + "," +
                    highlights + "," +
                    trip.getIdealDuration() + "," +
                    trip.getDestination().getTypicalBudget() + "," +
                    trip.getStartDate() + "," + 
                    trip.getEndDate() + "," +
                    companions + "," +
                    trip.getTravelStyle() + "," +
                    trip.getOverallRating() + "," +
                    loved + "," +
                    disliked + "," +
                    trip.getBestMemory();
                
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Trip logs saved succesfully!");

        } catch (IOException e) {
            System.out.println("Error saving trip logs: " + e.getMessage());
        }
    }


    public static List<TripLog> loadTripLogs() {
        List<TripLog> trips = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return trips;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);

                // Destination
                String name = parts[0];
                String country = parts[1];

                Contient contient = parts[2].isBlank()
                    ? null 
                    : Contient.valueOf(parts[2]);

                Climate climate = parts[3].isBlank()
                    ? null
                    : Climate.valueOf(parts[3]);

                Set<TripType> tripTypes =
                    parts[4].isBlank() 
                        ? new HashSet<>() 
                        : Arrays.stream(parts[4].split(";"))
                            .map(TripType::valueOf)
                            .collect(Collectors.toSet());

                Set<String> highlights =
                    parts[5].isBlank()
                        ? new HashSet<>()
                        : new HashSet<>(Arrays.asList(parts[5].split(";")));

                int idealDuration = Integer.parseInt(parts[6]);

                BudgetLevel budget = parts[7].isBlank()
                    ? null 
                    : BudgetLevel.valueOf(parts[7]);

                Destination destination = new Destination (
                    name,
                    country,
                    contient,
                    climate,
                    tripTypes,
                    highlights,
                    idealDuration,
                    budget
                );

                LocalDate startDate = LocalDate.parse(parts[8]);
                LocalDate endDate = LocalDate.parse(parts[9]);

                
                List<String> travelCompanions = 
                    parts[10].isBlank() 
                        ? new ArrayList<>() 
                        : new ArrayList<>(Arrays.asList(parts[10].split(";")));
                

                TravelStyle travelStyle = 
                    TravelStyle.valueOf(parts[11]);

                Rating overallRating = 
                    Rating.valueOf(parts[12]);

                
                Set<String> lovedAspects = 
                    parts[13].isBlank()
                        ? new HashSet<>() 
                        : new HashSet<>(Arrays.asList(parts[13].split(";")));

                
                Set<String> dislikedAspects = 
                    parts[14].isBlank()
                        ? new HashSet<>()
                        : new HashSet<>(Arrays.asList(parts[14].split(";")));

                String bestMemory = parts[15];



                TripLog trip = new TripLog (
                    destination, 
                    idealDuration, 
                    startDate, 
                    endDate, 
                    travelCompanions, 
                    travelStyle, 
                    overallRating, 
                    lovedAspects, 
                    dislikedAspects, 
                    bestMemory
                );

                trips.add(trip);
            }

            System.out.println("Trip logs loaded successfully.");

        } catch (IOException e) {
            System.out.println("Error loading trip logs: " + e.getMessage());
        }
        
        return trips;
    }
}
