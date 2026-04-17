import java.util.HashMap;
import java.util.Map;

public class TrainConsistManagementApp {
    public static void main(String[] args) {
        System.out.println("=== Train Consist Management App - UC6: Map Bogie to Capacity ===\n");

        // Create a HashMap to store bogie-capacity mapping
        Map<String, Integer> bogieCapacity = new HashMap<>();

        // Insert bogie-capacity pairs using put() method
        bogieCapacity.put("Sleeper", 72);
        bogieCapacity.put("AC Chair", 96);
        bogieCapacity.put("First Class", 48);
        bogieCapacity.put("General", 120);
        bogieCapacity.put("Goods - Rectangular", 500);

        // Display bogie capacity information
        System.out.println("Bogie Capacity Mapping:");
        System.out.println("------------------------");

        // Iterate over the map using entrySet()
        for (Map.Entry<String, Integer> entry : bogieCapacity.entrySet()) {
            System.out.println("Bogie: " + entry.getKey() + " | Capacity: " + entry.getValue() + " units");
        }

        System.out.println("\n--- Fast Lookup Example ---");
        String bogieName = "Sleeper";
        if (bogieCapacity.containsKey(bogieName)) {
            System.out.println("Capacity of " + bogieName + ": " + bogieCapacity.get(bogieName) + " seats");
        }

        System.out.println("\nTotal bogies in formation: " + bogieCapacity.size());
    }
}
