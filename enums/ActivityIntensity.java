package enums;

public enum ActivityIntensity {
    SEDENTARY("Sedentary", "Mostly Relaxing", 0, 2),
    LIGHT("Light", "Light Activities", 2, 4),
    MODERATE("Moderate", "Hiking, Biking, Active Days", 4, 6),
    HIGH("High", "Sports, Adventure, Strenous", 6, 8),
    EXTREME("Extreme", "Climbing, Diving, Extreme Sports", 8, 10);

    private final String type;
    private final String description;
    private final int minIntensity;
    private final int maxIntensity;

    ActivityIntensity(
        String type, 
        String description,
        int minIntensity,
        int maxIntensity
    ) {
        this.type = type;
        this.description = description;
        this.minIntensity = minIntensity;
        this.maxIntensity = maxIntensity;
    }

    public static ActivityIntensity fromType(String name) {
        for (ActivityIntensity a : values()) {
            if (a.type.equalsIgnoreCase(name)) {
                return a;
            }
        }
        throw new IllegalArgumentException("Unknown activity type: " + name);
    }


    public boolean isHighIntensity(int intensity) {
        return intensity >= 6;
    }

    public String getType() { return type; }
    public String getDescription() { return description; }
    public int getMinIntensity() { return minIntensity; }
    public int getMaxIntensity() { return maxIntensity; }

    @Override
    public String toString() {
        return type + " - " + description;
    }
}
