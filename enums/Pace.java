package enums;

public enum Pace {
    RELAXED("Relaxed", "Slow, Leisurely"),
    MODERATE("Moderate", "Balanced"),
    FAST_PACED("Fast-Paced", "Packed Itinerary"),
    FLEXIBLE("Flexible", "Adaptable"),
    SLOW_TRAVEL("Slow-Travel", "Immersive, Long Stays");

    private final String type;
    private final String description;

    Pace(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public static Pace fromType(String name) {
        for (Pace p : values()) {
            if (p.type.equalsIgnoreCase(name)) {
                return p;
            }
        }
        throw new IllegalArgumentException("Unknown pace type: " + name);
    }

    public boolean isFastPaced() {
        return this == FAST_PACED;
    }

    public boolean isRelaxed() {
        return this == RELAXED;
    }


    public String getType() { return type; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return type + " - " + description;
    }
}
