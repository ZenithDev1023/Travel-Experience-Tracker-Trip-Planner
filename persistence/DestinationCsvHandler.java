package persistence;

import model.Destination;

import enums.Contient;
import enums.BudgetLevel;
import enums.Climate;
import enums.TripType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Set;

import javax.management.RuntimeErrorException;

import java.util.HashSet;
import java.util.List;
import java.util.Arrays;


public class DestinationCsvHandler {
    private static final String FILE_NAME = "data/destinations.csv";
    private static final int MAX_DESTINATIONS = 200;


    public static Destination[] loadDestinations() {
        Destination[] destinations = new Destination[MAX_DESTINATIONS];
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            reader.readLine();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                String name = parts[0];
                String country = parts[1];
                Contient contient = Contient.valueOf(parts[2]);
                Climate climate = Climate.valueOf(parts[3]);

                String[] tripTypeParts = parts[4].split(";");
                Set<TripType> suitableTripTypes = new HashSet<>();
                for (String t : tripTypeParts) {
                    suitableTripTypes.add(TripType.valueOf(t.trim()));
                }

                String[] highlightParts = parts[5].split(";");
                Set<String> highlights = new HashSet<>(Arrays.asList(highlightParts));

                int idealDuration = Integer.parseInt(parts[6]);
                BudgetLevel typicalBudget = BudgetLevel.valueOf(parts[7]);

                destinations[count++] = new Destination(name, country, contient, climate, suitableTripTypes, highlights, idealDuration, typicalBudget);
            }
        } catch (IOException e) {
            System.out.println("Failed to load destinations: " + e.getMessage());
            return new Destination[MAX_DESTINATIONS];
        }

        return destinations;
    }

    public static void saveDestinations(List<Destination> destinations) {

        try {
            File original = new File(FILE_NAME);
            File backup = new File("data/backup.csv");

            if (original.exists()) {
                Files.copy(original.toPath(), backup.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (Exception e) {
            throw new RuntimeException("Backup failed", e);
        }

        int count = 0;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write("name,country,contient,climate,suitableTripTypes,highlights,idealDuration,typicalBudget");
            writer.newLine();

            for (int i = 0; i < count; i++) {

                Destination d = destinations.get(i);

                if (d == null) continue;
                
                writer.write(
                    d.getName() + "," +
                    d.getCountry() + "," +
                    d.getContient() + "," +
                    d.getClimate() + "," +
                    d.getSuitableTripTypes() + "," +
                    d.getHighlights() + "," +
                    d.getIdealDuration() + "," +
                    d.getTypicalBudget()
                );
                writer.newLine();
            }
    
        } catch (IOException e) {
            throw new RuntimeException("Failed to save destinations ", e);
        }
    }
}
