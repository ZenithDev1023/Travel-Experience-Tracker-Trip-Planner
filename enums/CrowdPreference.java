package enums;

public enum CrowdPreference {
    QUIET("Quiet", "Avoid Crowds"),
    MODERATE("Moderate", "Some People Okay"),
    SOCIAL("Social", "Enjoy Meeting People"),
    LONELY("Lonely", "Prefer Isolation"),
    PARTY("Party", "Enjoy Crowds, Nightlife");

    private final String type;
    private final String description;

    CrowdPreference(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public static CrowdPreference fromType(String name) {
        for (CrowdPreference c : values()) {
            if (c.type.equalsIgnoreCase(name)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Unknown Crowdpreference Type: " + name);
    }

    public String getType() { return type; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return type + " - " + description; 
    }
}
