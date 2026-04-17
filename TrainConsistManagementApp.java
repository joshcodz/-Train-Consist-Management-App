import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Bogie {
    private String name;
    private int capacity;

    public Bogie(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return name + " (" + capacity + " seats)";
    }
}

public class TrainConsistManagementApp {
    public static void main(String[] args) {
        System.out.println("=== Train Consist Management App - UC8: Filter Passenger Bogies Using Streams ===\n");

        // Create a List to store bogie objects
        List<Bogie> bogies = new ArrayList<>();

        // Add passenger bogies with their capacities
        bogies.add(new Bogie("Sleeper", 72));
        bogies.add(new Bogie("AC Chair", 96));
        bogies.add(new Bogie("First Class", 48));
        bogies.add(new Bogie("General", 120));
        bogies.add(new Bogie("AC Sleeper", 60));

        System.out.println("Original Bogie List:");
        System.out.println("-------------------");
        for (Bogie bogie : bogies) {
            System.out.println("  " + bogie);
        }

        // UC8: Filter using Stream API
        System.out.println("\n=== UC8: Stream Filtering Examples ===\n");

        // Filter 1: Capacity greater than 70
        System.out.println("Filter 1: High-capacity bogies (capacity > 70):");
        System.out.println("----------------------------------------------");
        List<Bogie> highCapacityBogies = bogies.stream()
                .filter(bogie -> bogie.getCapacity() > 70)
                .collect(Collectors.toList());
        
        if (highCapacityBogies.isEmpty()) {
            System.out.println("  No bogies found.");
        } else {
            for (Bogie bogie : highCapacityBogies) {
                System.out.println("  " + bogie);
            }
        }
        System.out.println("  Total high-capacity bogies: " + highCapacityBogies.size());

        // Filter 2: Capacity equal to threshold value (70)
        System.out.println("\nFilter 2: Bogies with capacity equal to 70:");
        System.out.println("-------------------------------------------");
        List<Bogie> equalThresholdBogies = bogies.stream()
                .filter(bogie -> bogie.getCapacity() == 70)
                .collect(Collectors.toList());
        
        if (equalThresholdBogies.isEmpty()) {
            System.out.println("  No bogies found with capacity = 70.");
        } else {
            for (Bogie bogie : equalThresholdBogies) {
                System.out.println("  " + bogie);
            }
        }

        // Filter 3: Capacity less than 70
        System.out.println("\nFilter 3: Low-capacity bogies (capacity < 70):");
        System.out.println("----------------------------------------------");
        List<Bogie> lowCapacityBogies = bogies.stream()
                .filter(bogie -> bogie.getCapacity() < 70)
                .collect(Collectors.toList());
        
        if (lowCapacityBogies.isEmpty()) {
            System.out.println("  No bogies found.");
        } else {
            for (Bogie bogie : lowCapacityBogies) {
                System.out.println("  " + bogie);
            }
        }
        System.out.println("  Total low-capacity bogies: " + lowCapacityBogies.size());

        // Filter 4: Multiple conditions - capacity >= 60 AND <= 100
        System.out.println("\nFilter 4: Medium-capacity bogies (capacity between 60 and 100):");
        System.out.println("-------------------------------------------------------------");
        List<Bogie> mediumCapacityBogies = bogies.stream()
                .filter(bogie -> bogie.getCapacity() >= 60 && bogie.getCapacity() <= 100)
                .collect(Collectors.toList());
        
        if (mediumCapacityBogies.isEmpty()) {
            System.out.println("  No bogies found.");
        } else {
            for (Bogie bogie : mediumCapacityBogies) {
                System.out.println("  " + bogie);
            }
        }
        System.out.println("  Total medium-capacity bogies: " + mediumCapacityBogies.size());

        // Verify original list integrity
        System.out.println("\n=== Verify Original Collection Integrity ===");
        System.out.println("Original collection size: " + bogies.size());
        System.out.println("Original bagios unchanged: " + (bogies.size() == 5));

        System.out.println("\n=== Stream Filtering Complete ===");
    }
}
