package enums;

public enum TravelStyle {
    SOLO("Solo", "Traveling Alone"),
    COUPLE("Couple", "Romantic Travel"),
    FAMILY("Family", "With Children"),
    FRIENDS("Friends", "Group Of Friends"),
    BUSINESS("Business", "Work-Related"),
    GROUP_TOUR("Group-Tour", "Organized Tour"),
    SOLO_BUT_SOCIAL("Solo-But-Social", "Alone but Joining Groups"),
    MULTI_GENERATIONAL("Multi-Generational", "Extended Family");

    private final String type;
    private final String description;

    TravelStyle(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public static TravelStyle fromTravelStyle(String name) {
        for (TravelStyle s : values()) {
            if (s.type.equalsIgnoreCase(name)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown travel style: " + name);
    }

    public String getType() { return type; }
    public String getDescription() { return description; }

}
