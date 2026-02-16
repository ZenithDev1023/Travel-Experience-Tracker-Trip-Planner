package enums;

public enum PlanningStage {
    DREAMING("Dreaming", "Just an idea"),
    RESEARCHING("Researching", "Gathering information"),
    SAVING("Saving", "Financially preparing"),
    PLANNING("Planning", "Itinerary building"),
    BOOKED("Booked", "Travel arranged"),
    COMPLETED("Completed", "Trip finished"),
    POSTPONED("Postponed", "Trip delayed"),
    CANCELLED("Cancelled", "Trip cancelled");

    private final String type;
    private final String description;

    PlanningStage(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public static PlanningStage fromType(String name) {
        for (PlanningStage p : values()) {
            if (p.type.equalsIgnoreCase(name)) {
                return p;
            }
        }
        throw new IllegalArgumentException("Unknown planning type: " + name);
    }

    public String getType() { return type; }
    public String getDescription() { return description; }
    
    @Override
    public String toString() {
        return type + " - " + description;
    }
}
