package enums;

public enum TripType {
    ADVENTURE("Adventure", "Hiking, Skiing, Extreme Sports"),
    RELAXATION("Relaxation", "Beach, Spa, Quiet Retreat"),
    CULTURAL("Cultural", "Museums, Temples, Historical Sites"),
    FOOD("Food", "Culinary Tours, Food Festivals"),
    NATURE("Nature", "National Parks, Wildlife, Scenery"),
    CITY("City", "Urban Exploration, Shopping, Nightlife"),
    ROAD_TRIP("Road Trip", "Driving, Camping, Exploration"),
    VOLUNTEER("Volunteer", "Community Service, Conservation"),
    LUXURY("Luxury", "High-End Resorts, Exclusive Experiences"),
    LUXURY_SPA("Luxury Spa", "High-end Spas"),
    FAMILY("Family", "Family Resorts, Family Activities"),
    ADULT("Adult", "Adult Resorts, Adult Activities"),
    BUDGET("Budget", "Backpacking, Hostel, Low-Cost");

    private final String type;
    private final String description;

    TripType(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() { return type; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return type + " - " + description;
    }
}
