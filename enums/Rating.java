package enums;

public enum Rating {
    TERRIBLE("Terrible", "Hated It", 1, 1),
    POOR("Poor", "Disappointed", 2, 2),
    OKAY("Okay", "Acceptable", 3, 3),
    GOOD("Good", "Enjoyed It", 4, 4),
    GREAT("Great", "Loved It", 5, 5),
    AMAZING("Amazing", "Expectional", 5, 10),
    NOT_RATED("Not-Rated", "Haven't Rated Yet"),
    MIXED("Mixed", "Some Good, Some Bad", 2, 3);

    private final String type;
    private final String description;
    private final int minScore;
    private final int maxScore;

    Rating(String type, String description) {
        this(type, description, 0, 0);
    }

    Rating(String type, String description, int minScore, int maxScore) {
        this.type = type;
        this.description = description;
        this.minScore = minScore;
        this.maxScore = maxScore;
    }


    public static Rating fromRating(String name) {
        for (Rating r : values()) {
            if (r.type.equalsIgnoreCase(name)) {
                return r;
            }
        }
        throw new IllegalArgumentException("Unknown rating type: " + name);
    }


    public boolean isHighlyRated() {
        return minScore >= 4;
    }

    public boolean isPoorlyRated() {
        return maxScore <= 2;
    }

    public String getType() { return type; }
    public String getDescription() { return description; }
    public int getMinScore() { return minScore; }
    public int getMaxScore() { return maxScore; }
}
