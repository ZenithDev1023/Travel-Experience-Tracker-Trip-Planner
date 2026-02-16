package enums;

public enum Priority {
    URGENT("Urgent", "Book Soon, Time-Sensitive", 5),
    HIGH("High", "Important, Plan Within Year", 4),
    MEDIUM("Medium", "Would Be Nice, Within 2 Years", 3),
    LOW("Low", "Someday, No Rush", 2),
    DREAM("Dream", "Unlikely But Aspirational", 1),
    SEASONAL("Seasonal", "Specific Time Of Year", 2),
    FLEXIBLE("Flexible", "Whenever Opportunity Arises", 1);  
    
    private final String type;
    private final String description;
    private final int priorityScore;

    Priority(String type, String description) {
        this(type, description, 0);
    }

    Priority(String type, String description, int priorityScore) {
        if (priorityScore < 0 || priorityScore > 5) {
            throw new IllegalArgumentException("Priority score must be between 0 and 5");
        }

        this.type = type;
        this.description = description;
        this.priorityScore = priorityScore;
    }

    public static Priority fromPriority(String name) {
        for (Priority p : values()) {
            if (p.type.equalsIgnoreCase(name)) {
                return p;
            }
        }
        throw new IllegalArgumentException("Unknown priority type: " + name);
    }

    public boolean isHighPriority() {
        return priorityScore >= HIGH.priorityScore;
    }

    public boolean isTimeSensitive() {
        return this == URGENT || this == SEASONAL;
    }

    public boolean isAspirational() {
        return this == DREAM;
    }

    public String getType() { return type; }
    public String getDescription() { return description; }
    public int getPriorityScore() { return priorityScore; }

    @Override
    public String toString() {
        return type + " - " + description;
    }
}
