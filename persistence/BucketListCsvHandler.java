package persistence;

import model.BucketListItem;
import model.Destination;

import enums.Priority;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;


public class BucketListCsvHandler {
    private static final String FILE_NAME = "data/bucket_list.csv";
    private static final int MAX_BUCKET_LIST = 200;
    
    public static BucketListItem[] loadBucketList() {
        BucketListItem[] bucketListItems = new BucketListItem[MAX_BUCKET_LIST];
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            reader.readLine();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                Destination destination = new Destination(parts[0]);
                Priority priority = Priority.valueOf(parts[1]);
                String notes = parts[2];
                int target_year = Integer.parseInt(parts[3]);

                bucketListItems[count++] = new BucketListItem(destination, priority, notes, target_year, notes, null, null, null);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new BucketListItem[MAX_BUCKET_LIST];
        }

        return bucketListItems;
    }


    public static void saveBucketList(BucketListItem[] bucketListItems, int count) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write("destination,priority,notes,target_year");
            writer.newLine();

            for (int i = 0; i < count; i++) {
                BucketListItem b = bucketListItems[i];
                
                writer.write(
                    b.getDestination().getName() + "," +
                    b.getPriority() + "," +
                    b.getNotes() + "," +
                    b.getTargetYear()
                );
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save bucket-list ", e);
        }
    }
    
}
