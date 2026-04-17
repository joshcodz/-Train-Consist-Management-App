import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Bogie {
    private String name;
    private String type;
    private int capacity;

    public Bogie(String name, String type, int capacity) {
        this.name = name;
        this.type = type;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return name + " (" + type + ", " + capacity + " seats)";
    }
}

public class TrainConsistManagementApp {
    public static void main(String[] args) {
        System.out.println("=== Train Consist Management App - UC9: Group Bogies by Type ===\n");

        // Create a List to store bogie objects
        List<Bogie> bogies = new ArrayList<>();

        // Add passenger bogies with their types and capacities
        bogies.add(new Bogie("Sleeper", "Passenger", 72));
        bogies.add(new Bogie("AC Chair", "Passenger", 96));
        bogies.add(new Bogie("First Class", "Passenger", 48));
        bogies.add(new Bogie("General", "Passenger", 120));
        bogies.add(new Bogie("AC Sleeper", "Passenger", 60));
        bogies.add(new Bogie("Rectangular", "Goods", 500));
        bogies.add(new Bogie("Cylindrical", "Goods", 400));

        System.out.println("Original Bogie List:");
        System.out.println("-------------------");
        for (Bogie bogie : bogies) {
            System.out.println("  " + bogie);
        }

        // UC9: Group using Stream API and Collectors.groupingBy()
        System.out.println("\n=== UC9: Stream Grouping by Bogie Type ===\n");

        // Group 1: Group bogies by type (Passenger vs Goods)
        System.out.println("Grouping 1: Bogies grouped by Type:");
        System.out.println("-----------------------------------");
        Map<String, List<Bogie>> bogiesByType = bogies.stream()
                .collect(Collectors.groupingBy(Bogie::getType));

        for (String type : bogiesByType.keySet()) {
            System.out.println("\n  Type: " + type);
            List<Bogie> boggiesOfType = bogiesByType.get(type);
            for (Bogie bogie : boggiesOfType) {
                System.out.println("    - " + bogie);
            }
            System.out.println("  Total: " + boggiesOfType.size() + " bogies");
        }

        // Group 2: Group passenger bogies by name (further classification)
        System.out.println("\nGrouping 2: Passenger bogies grouped by Name:");
        System.out.println("---------------------------------------------");
        Map<String, List<Bogie>> passengerBogiesByName = bogies.stream()
                .filter(bogie -> "Passenger".equals(bogie.getType()))
                .collect(Collectors.groupingBy(Bogie::getName));

        for (String name : passengerBogiesByName.keySet()) {
            System.out.println("\n  Name: " + name);
            List<Bogie> boggiesWithName = passengerBogiesByName.get(name);
            for (Bogie bogie : boggiesWithName) {
                System.out.println("    - " + bogie);
            }
            System.out.println("  Total: " + boggiesWithName.size() + " bogies");
        }

        // Group 3: Group bogies by capacity range
        System.out.println("\nGrouping 3: Bogies grouped by Capacity Range:");
        System.out.println("---------------------------------------------");
        Map<String, List<Bogie>> bogiesByCapacityRange = bogies.stream()
                .collect(Collectors.groupingBy(bogie -> {
                    if (bogie.getCapacity() < 100) {
                        return "Low Capacity (< 100)";
                    } else if (bogie.getCapacity() <= 200) {
                        return "Medium Capacity (100-200)";
                    } else {
                        return "High Capacity (> 200)";
                    }
                }));

        for (String capacityRange : bogiesByCapacityRange.keySet()) {
            System.out.println("\n  Range: " + capacityRange);
            List<Bogie> boggiesInRange = bogiesByCapacityRange.get(capacityRange);
            for (Bogie bogie : boggiesInRange) {
                System.out.println("    - " + bogie);
            }
            System.out.println("  Total: " + boggiesInRange.size() + " bogies");
        }

        // Verify original list integrity
        System.out.println("\n=== Verify Original Collection Integrity ===");
        System.out.println("Original collection size: " + bogies.size());
        System.out.println("Original bogies unchanged: " + (bogies.size() == 7));

        System.out.println("\n=== Stream Grouping Complete ===");
    }
}
