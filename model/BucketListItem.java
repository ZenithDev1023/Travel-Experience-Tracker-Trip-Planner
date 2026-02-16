package model;

import model.Destination;
import enums.DurationCategory;
import enums.Pace;
import enums.Priority;
import enums.Season;

import utils.ValidationBucketList;

public class BucketListItem {
    // Fields
    private Destination destination; // Class
    private Priority priority; // Enum
    private String notes;
    private int targetYear;
    private String inspirationSource;   
    private DurationCategory duration;
    private Pace pace;
    private Season season;


    // Constructor
    public BucketListItem(
        Destination destination,
        Priority priority,
        String notes,
        int targetYear,
        String inspirationSource,
        DurationCategory duration,
        Pace pace,
        Season season
    ) {
        ValidationBucketList.validateDestination(destination);
        ValidationBucketList.validatePriority(priority);
        ValidationBucketList.validateNotes(notes);
        ValidationBucketList.validateTargetYear(targetYear);
        ValidationBucketList.validateInspirationSource(inspirationSource);
        ValidationBucketList.validateDuration(duration);
        ValidationBucketList.validatePace(pace);
        ValidationBucketList.validateSeason(season);

        this.destination = destination;
        this.priority = priority;
        this.notes = notes;
        this.targetYear = targetYear;
        this.inspirationSource = inspirationSource;
        this.duration = duration;
        this.pace = pace;
        this.season = season;
    }


    public String getUrgency(Destination destination) {
        
        return priority.toString();
    }


    public boolean isFeasible() {
        if (priority == Priority.DREAM) {
            return false;
        }

        if (pace == Pace.FAST_PACED && duration == DurationCategory.DAY_TRIP) {
            return false;
        }

        if (duration == DurationCategory.GAP_YEAR && season == Season.PEAK) {
            return false;
        }

        return true;
    }


    public String toPlanningItem() {
        return String.format(
            "%s, %s, %s, %d, %s, %s, %s, %s",
            destination, priority, notes, targetYear, inspirationSource, duration, pace, season
        );
    }
    

    // Getters
    public Destination getDestination() { return destination; }
    public Priority getPriority() { return priority; }
    public String getNotes() { return notes; }
    public int getTargetYear() { return targetYear; }
    public String getInspirationSource() { return inspirationSource; }
    public DurationCategory getDuration() { return duration; }
    public Pace getPace() { return pace; }
    public Season getSeason() { return season; }

    @Override
    public String toString() {
        return String.format(
            "%s, %s, %s, %d, %s",
            destination, priority, notes, targetYear, inspirationSource
        );
    }
}
