package enums;

public enum Climate {
    TROPICAL("Tropical", "Hot and Humid Year-Round"),
    TEMPERATE("Temperate", "Four Distinct Seasons"),
    ARCTIC("Arctic", "Very Cold, Snow"),
    DESERT("Desert", "Hot Days, Cold Nights, Dry"),
    MEDITERRANEAN("Mediterranean", "Mild Winters, Warm Summers"),
    CONTINENTAL("Continental", "Large Temperature Variations"),
    MOUNTAIN("Mountain", "Cooler With Altitude"),
    HUMID_SUBTROPICAL("Humid-Subtropical", "Hot Summer, Mild Winters");

    private final String type;
    private final String description;

    Climate(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() { return type; }
    public String getDescription() { return description; }
}
