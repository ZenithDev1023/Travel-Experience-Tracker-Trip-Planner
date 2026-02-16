package enums;

public enum BudgetLevel {
    BACKPACKER("Backpacker", "Ultra-Budget, Hostels, Street Food", 50),
    BUDGET("Budget", "Affordable Hotels, Local Restaurants", 100),
    MODERATE("Moderate", "Comfortable, Mix Of Experiences", 200),
    LUXURY("Luxury", "High-End Hotels, Fine Dining", 500),
    SPLURGE("Splurge", "No Budget Constraints, Exclusive", 1000),
    VARIABLE("Variable", "Depends On Destination", 50, 1000),
    ALL_INCLUSIVE("All Inclusive", "Package Deals", 200, 1000);

   private final String type;
   private final String description;
   private final int minPrice;
   private final int maxPrice;

   BudgetLevel(String type, String description, int minPrice) {
    this(type, description, minPrice, 0);
   }

   BudgetLevel(
    String type,
    String description,
    int minPrice,
    int maxPrice
   ) {
    this.type = type;
    this.description = description;
    this.minPrice = minPrice;
    this.maxPrice = maxPrice;
   }

   public static BudgetLevel fromBudget(String name) {
    for (BudgetLevel b : values()) {
        if (b.type.equalsIgnoreCase(name)) {
            return b;
        }
    }
    throw new IllegalArgumentException("Unknown budget type: " + name);
   }

   public boolean isExpensive() {
    return minPrice >= 500;
   }

   public boolean isCheap() { 
    return minPrice <= 200;
   }


   public String getType() { return type; }
   public String getDescription() { return description; }
   public int getMinPrice() { return minPrice; }
   public int getMaxPrice() { return maxPrice; }
}
