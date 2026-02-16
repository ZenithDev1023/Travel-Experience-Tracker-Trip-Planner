import model.Recommendation;
import utils.ConsoleColors;

import service.DestinationMatcher;
import service.MemoryGenerator;
import service.PatternAnalyzer;
import service.PreferenceUpdater;
import service.TripPlanner;

import model.Destination;
import model.TravelPreference;
import model.TripLog;

import enums.Rating;
import enums.Season;
import enums.TravelStyle;
import enums.TripType;
import enums.Contient;
import enums.Climate;
import enums.BudgetLevel;

import persistence.CsvFileManager;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

import java.util.Scanner;
import java.time.LocalDate;

import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<TripLog> travelHistory = new ArrayList<>();
        List<String> summaryTemplates = new ArrayList<>();
        Map<String, String> emojiMap = new HashMap<>();

        Set<Destination> destinations = new HashSet<>();
        List<Destination> availableDestinations = new ArrayList<>();

        TravelPreference userPreference = null;


        DestinationMatcher destinationApp = new DestinationMatcher(travelHistory);
    
        MemoryGenerator MemoryApp = 
            new MemoryGenerator(travelHistory, summaryTemplates, emojiMap);
        PatternAnalyzer PatternApp = 
            new PatternAnalyzer(travelHistory, destinations, userPreference, new HashMap<>());
        PreferenceUpdater preferenceApp = 
            new PreferenceUpdater(userPreference, travelHistory, new HashMap<>(), 0.1);
        TripPlanner tripApp =  
            new TripPlanner(availableDestinations, userPreference, travelHistory);

        CsvFileManager csvFileManager = 
            new CsvFileManager(destinationApp, travelHistory);

        csvFileManager.loadAll();

        boolean running = true;

        while (running) {
            System.out.println(ConsoleColors.BRIGHT_CYAN.format("\nTravel Experience Tracker & Trip Planner v1.0 "));
            System.out.println("======================================");
            System.out.println("1. 📊 View Travel Dashboard");
            System.out.println("2. 🗺️ Plan New Trip");
            System.out.println("3. 📝 Log Past Trip");
            System.out.println("4. 🎯 Get Recommendations");
            System.out.println("5. 📈 View Analytics");
            System.out.println("6. ⚙️ Manage Preferences");
            System.out.println("7. 💾 Save & Export data");
            System.out.println("8. ❌ Exit");

            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    if (travelHistory == null || travelHistory.isEmpty()) {
                        System.out.println("No trips recorded yet.");
                        break;
                    }

                    System.out.println("\n=============================\n");
                    System.out.println("🗺️ TRAVEL DASHBOARD");
                    System.out.println("\n=============================\n");

                    System.out.println("📌 RECENT TRIPS");
                    for (TripLog trip : travelHistory) {
                        System.out.println("----------------------------------");
                        System.out.println("📍 " + trip.getDestination().getName() + " " + trip.getDestination().getCountry() + " " + "(" + trip.getDestination().getContient() + ")");
                        System.out.println("📅 " + trip.getStartDate() + " - " + trip.getEndDate());
                        System.out.println("🌤️ Climate: " + trip.getDestination().getClimate());
                        System.out.println("💰 Budget: " + trip.getDestination().getTypicalBudget());
                        System.out.println("⭐ Rating: " + trip.getOverallRating());
                        System.out.println("👨‍👩‍👧‍👦 Style: " + trip.getDestination().getSuitableTripTypes());
                        System.out.println("🌟 BEST MEMORY \n" + trip.getBestMemory());
                        System.out.println("------------------------\n");
                    }

                    break;
                
                case 2:
                    try {
                        System.out.print("Destination: ");
                        String name = scanner.nextLine();

                        if (name.trim().isEmpty()) {
                            System.out.println(ConsoleColors.RED + "❌ Destination cannot be empty!" + ConsoleColors.RESET);
                            break;
                        }

                        Destination destination = new Destination(name);

                        System.out.print("Start Date (YYYY-MM-DD): ");
                        LocalDate startDate = LocalDate.parse(scanner.nextLine());

                        System.out.print("How Many Days: ");
                        int days = Integer.parseInt(scanner.nextLine());

                        if (days <= 0) {
                            System.out.println(ConsoleColors.YELLOW + "⚠ Days must be positive. Using 1 day." + ConsoleColors.RESET);
                            days = 1;
                        }

                        LocalDate endate = startDate.plusDays(days);
                        System.out.println(ConsoleColors.CYAN + "Trip: " + name + " from " + startDate + " to " + endate + " (" + days + " days)" + ConsoleColors.RESET);

                        tripApp.createTripPlan(destination, startDate, days);
                        System.out.println(ConsoleColors.GREEN + "✅ Successfully planned new trip!" + ConsoleColors.RESET);


                    } catch (DateTimeParseException e) {
                        System.out.println(ConsoleColors.RED + "❌ Invalid date format. Use YYYY-MM-DD" + ConsoleColors.RESET);
                    } catch (NumberFormatException e) {
                        System.out.println(ConsoleColors.RED + "❌ Invalid number format days" + ConsoleColors.RESET);
                    } catch (Exception e) {
                        System.out.println(ConsoleColors.RED + "❌ Error! " + e.getMessage() + ConsoleColors.RESET);
                    }

                    break;

                case 3:
                    // Destination 
                    System.out.println("📍 DESTINATION DETAILS");
                    System.out.println("----------------------");

                    System.out.print("Enter Destination: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter Country: ");
                    String country = scanner.nextLine();

                    System.out.print("Enter Contient (ASIA/EUROPE/etc): ");
                    Contient contient = Contient.valueOf(scanner.nextLine().toUpperCase());

                    System.out.print("Enter Climate (TEMPERATE/TROPICAL/etc): ");
                    Climate climate = Climate.valueOf(scanner.nextLine().toUpperCase());

                    System.out.print("Enter Suitable Trip Types (comma-separated): ");
                    System.out.println("\nAvailable types: ADVENTURE, RELAXATION, CULTURAL, FOOD, NATURE, CITY, ROAD_TRIP, VOLUNTEER, LUXURY, LUXURY_SPA, FAMILY, ADULT, BUDGET");
                    Set<TripType> suitableTripTypes = new HashSet<>(
                        Arrays.stream(scanner.nextLine().split(","))
                                .map(String::trim)
                                .map(String::toUpperCase)
                                .map(TripType::valueOf)
                                .collect(Collectors.toSet())
                    );

                    System.out.print("Enter Highlights (comma-separated): ");
                    Set<String> highlights = new HashSet<>(
                        Arrays.asList(scanner.nextLine().split(",\\s*"))
                    );

                    System.out.print("Enter ideal duration (days): ");
                    int idealDuration = Integer.parseInt(scanner.nextLine());

                    System.out.println("Enter Typical Budget (BUDGET/MODERATE/LUXURY): ");
                    BudgetLevel typicalBudget = BudgetLevel.valueOf(scanner.nextLine().toUpperCase());


                    Destination destination = new Destination(
                        name,
                        country,
                        contient,
                        climate,
                        suitableTripTypes,
                        highlights,
                        idealDuration,
                        typicalBudget
                    );


                    // Trip Log
                    System.out.println("\n📝 TRIP DETAILS");
                    System.out.println("----------------");

                    System.out.print("Start date (YYYY-MM-DD): ");
                    LocalDate startDate = LocalDate.parse(scanner.nextLine());

                    System.out.print("End date (YYYY-MM-DD): ");
                    LocalDate endDate = LocalDate.parse(scanner.nextLine());

                    System.out.print("Travel companions (comma-separated): ");
                    List<String> travelCompanions = Arrays.stream(scanner.nextLine().split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList());

                    System.out.print("Travel style (SOLO/COUPLE/FAMILY/FRIENDS): ");
                    TravelStyle travelStyle = TravelStyle.valueOf(scanner.nextLine().toUpperCase());

                    System.out.print("Rating (TERRIBLE/OKAY/GOOD/GREAT/AMAZING): ");
                    Rating rating = Rating.valueOf(scanner.nextLine().toUpperCase());

                    System.out.print("Loved aspects (comma-separated): ");
                    Set<String> lovedAspects = new HashSet<>(
                        Arrays.asList(scanner.nextLine().split(",\\s*"))
                    );

                    System.out.print("Disliked Aspects (comma-separated): ");
                    Set<String> dislikedAspects = new HashSet<>(
                        Arrays.asList(scanner.nextLine().split(",\\s*"))
                    );

                    System.out.print("Best Memory: ");
                    String bestMemory = scanner.nextLine();

                    TripLog log = new TripLog(
                        destination, 
                        idealDuration, 
                        startDate, 
                        endDate, 
                        travelCompanions, 
                        travelStyle, 
                        rating, 
                        lovedAspects, 
                        dislikedAspects, 
                        bestMemory
                    );

                    travelHistory.add(log);
                    csvFileManager.saveAll();
                    System.out.println("Trip logged and saved successfully!" + ConsoleColors.RESET);

                    break;

                case 4:
                    System.out.println("GET RECOMMENDATIONS");
                    System.out.println("---------------------");
                    System.out.print("Trip types (CULTURAL,NATURE,ADVENTURE): ");
                    Set<TripType> types = Arrays.stream(scanner.nextLine().toUpperCase().split(","))
                                    .map(String::trim)
                                            .map(TripType::valueOf)
                                                    .collect(Collectors.toSet());

                    System.out.print("Seasons (SPRING,SUMMER,AUTUMN,WINTER): ");
                    Set<Season> seasons = Arrays.stream(scanner.nextLine().toUpperCase().split(","))
                                    .map(String::trim)
                                            .map(Season::valueOf)
                                                    .collect(Collectors.toSet());

                    System.out.print("Budget Level (BACKPACKER,BUDGET,MODERATE,LUXURY,SPLURGE): ");
                    BudgetLevel budget = BudgetLevel.valueOf(scanner.nextLine().toUpperCase());

                    System.out.print("Travel Style (SOLO,COUPLE,FAMILY,FRIENDS,BUSINESS,GROUP_TOUR,SOLO_BUT_SOCIAL,MULTI_GENERATIONAL): ");
                    TravelStyle style = TravelStyle.valueOf(scanner.nextLine().toUpperCase());

                    System.out.print("Climate Preferences (TROPICAL,TEMPERATE,ARCTIC,DESERT,MEDITERRANEAN,CONTINENTAL,MOUNTAIN,HUMID_SUBTROPICAL): ");
                    Set<Climate> climates = Arrays.stream(scanner.nextLine().toUpperCase().split(","))
                                    .map(String::trim)
                                            .map(Climate::valueOf)
                                                    .collect(Collectors.toSet());

                    System.out.print("Deal Breakers (): ");
                    String dealInput = scanner.nextLine();
                    Set<String> db = dealInput.equalsIgnoreCase("none") ?
                            new HashSet<>() :
                            new HashSet<>(Arrays.asList(dealInput.split(",")));


                    TravelPreference preference = new TravelPreference(
                            types,
                            seasons,
                            budget,
                            style,
                            climates,
                            db,
                            LocalDate.now()
                    );

                    System.out.print("Season for recommendations: ");
                    Season season = Season.valueOf(scanner.nextLine().toUpperCase());

                    List<Recommendation> matches = destinationApp.findMatches(preference);
                    List<Recommendation> seasonalRecs = destinationApp.getSeasonalRecommendations(season);

                    System.out.println("\nRESULTS");
                    System.out.println("------------");
                    System.out.println("Matches: " + matches.size());
                    System.out.println("Seasonal: " + seasonalRecs.size());

                    break;

                case 5:
                    System.out.println("TRAVEL ANALYTICS");
                    System.out.println("------------------");

                    System.out.println("Favorite trip types: " + PatternApp.analyzeFavoriteTripType());
                    
                    System.out.println("Season Preferences: " + PatternApp.calculateSeasonPreferences());
                    System.out.println();

                    System.out.print("Enter Destination: ");
                    String destinationName = scanner.nextLine();

                    Destination searchDest = new Destination(destinationName);
                    TripLog foundTrip = null;

                    for (TripLog trip : travelHistory) {
                        if (trip != null && trip.getDestination() != null && trip.getDestination().getName().equalsIgnoreCase(destinationName)) {
                            foundTrip = trip;
                            break;
                        }
                    }

                    if (foundTrip != null) {
                        System.out.println(MemoryApp.generateTripSummary(foundTrip));
                    } else {
                        System.out.println("No trip found to " + foundTrip);
                    }
                    System.out.println();


                    System.out.print("Enter year: ");
                    try {
                        int year = Integer.parseInt(scanner.nextLine());
                        System.out.println(MemoryApp.generateYearInReview(year));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid year format");
                    }

                    break;

                case 6:
                    TravelPreference prefs = preferenceApp.getCurrentPreferences();

                    if (prefs == null) {
                        System.out.println("⚙️ CURRENT TRAVEL PREFERENCES");
                        System.out.println("-------------------------------");
                        System.out.println("No preferences set yet.");
                        System.out.println("Log some trips first or set preferences manually.");
                    } else {
                        System.out.println("⚙️ CURRENT TRAVEL PREFERENCES");
                        System.out.println("-------------------------------");
                        System.out.println("Trip Types: " + prefs.getPreferredTripType());
                        System.out.println("Climates: " + prefs.getClimatePreferences());
                        System.out.println("Seasons: " + prefs.getPreferredSeasons());
                        System.out.println("Budget: " + prefs.getBudgetRange());
                        System.out.println("Deal-breakers: " + prefs.getDealBreakers());
                        System.out.println("Last updated: " + prefs.getLastUpdated());
                    }

                    break;

                case 7:
                    csvFileManager.saveAll();
                    System.out.println("Saved data successfully!");
                    break;

                case 8:
                    running = false;
                    csvFileManager.saveAll();
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid input! Please try again.");
            }
        }

        scanner.close();
    }
}
