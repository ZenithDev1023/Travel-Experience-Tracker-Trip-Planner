package enums;

public enum DurationCategory {
    DAY_TRIP("Day Trip", "1 day", 1, 1),
    STOPOVER("Stop Over", "a few hours", 0, 0),
    WEEKEND("Weekend", "2-3 days", 2, 3),
    SHORT_BREAK("Short Break", "4-7 days", 4, 7),
    STANDARD("Standard", "8-14 days", 8, 14),
    EXTENDED("Extended", "15-30 days", 15, 30),
    LONG_TERM("Long Term", "1-3 months", 30, 90),
    GAP_YEAR("Gap Year", "3+ months", 90, 365);
    
    private final String type;
    private final String duration;
    private final int minDays;
    private final int maxDays;

    DurationCategory(
        String type, 
        String duration,
        int minDays,
        int maxDays
    ) {
        this.type = type;
        this.duration = duration;
        this.minDays = minDays;
        this.maxDays = maxDays;
    }

    public static DurationCategory fromDuration(String name) {
        for (DurationCategory d : values()) {
            if (d.type.equalsIgnoreCase(name)) {
                return d;
            }
        }
        throw new IllegalArgumentException("Unknown duration: " + name);
    }

    public String getType() { return type; }
    public String getDuration() { return duration; }
    public int getMindays() { return minDays; }
    public int getMaxDays() { return maxDays; }


    // Helper method
    public boolean fits(int days) {
        return days >= minDays && days <= maxDays;
    }

    @Override
    public String toString() {
        return type + " - " + duration;
    }
}
