package model;

import enums.Season;

public class SeasonRecommendation {
    // Fields
    private Season season;
    private String reason;
    private int matchScore; 

    public SeasonRecommendation(
        Season season,
        String reason,
        int matchScore
    ) {
        this.season = season;
        this.reason = reason;
        this.matchScore = matchScore;
    }


    public boolean isRecommended() {
        return matchScore >= 70;
    }

    public String toDisplayString() {
        return String.format(
            "%s: %s (%d%%)", 
            season, reason, matchScore
        );
    }

    // Getters
    public Season getSeason() { return season; }
    public String getReason() { return reason; }
    public int getMatchScore() { return matchScore; }
}
