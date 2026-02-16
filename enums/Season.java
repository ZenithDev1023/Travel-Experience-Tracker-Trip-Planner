package enums;

public enum Season {
    SPRING("Spring", "March-May"),
    SUMMER("Summer", "June-August"),
    AUTUMN("Autumn", "September-November"),
    WINTER("Winter", "December-February"),
    MONSOON("Monsoon", "Rainy Season"),
    DRY("Dry", "Dry Season"),
    PEAK("Peak", "High Season, Most Crowded", 3),
    SHOULDER("Shoulder", "Between Peak and Off-Peak", 2),
    OFF_PEAK("Off-Peak", "Low Season, Least Crowded", 1);

    private final String season;
    private final String months;
    private final int popularity;

    Season(String season, String months) {
        this(season, months, 0);
    }

    Season(String season, String months, int popularity) {
        this.season = season;
        this.months = months;
        this.popularity = popularity;
    }

    public static Season fromSeason(String name) {
        for (Season s : values()) {
            if(s.season.equalsIgnoreCase(name)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown season: " + name);
    }

    public boolean isBusy() {
        return popularity == 3;
    }

    public String getSeason() { return season; }
    public String getMonths() { return months; }
    public int getPopularity() { return popularity; }

    @Override
    public String toString() {
        return season + " - " + months;
    }
}
