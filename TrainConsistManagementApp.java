import java.util.ArrayList;
import java.util.List;

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
        System.out.println("=== Train Consist Management App - UC10: Count Total Seats Using reduce() ===\n");

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

        // UC10: Aggregate using Stream reduce()
        System.out.println("\n=== UC10: Stream Aggregation Using reduce() ===\n");

        // Calculate 1: Total seating capacity of all bogies
        System.out.println("Aggregation 1: Total Seating Capacity of All Bogies");
        System.out.println("-------------------------------------------------");
        int totalCapacity = bogies.stream()
                .map(Bogie::getCapacity)
                .reduce(0, Integer::sum);
        System.out.println("Total seating capacity: " + totalCapacity + " seats\n");

        // Calculate 2: Total capacity of passenger bogies only
        System.out.println("Aggregation 2: Total Capacity of Passenger Bogies Only");
        System.out.println("----------------------------------------------------");
        int passengerCapacity = bogies.stream()
                .filter(bogie -> "Passenger".equals(bogie.getType()))
                .map(Bogie::getCapacity)
                .reduce(0, Integer::sum);
        System.out.println("Total passenger seating: " + passengerCapacity + " seats\n");

        // Calculate 3: Total capacity of goods bogies
        System.out.println("Aggregation 3: Total Capacity of Goods Bogies");
        System.out.println("---------------------------------------------");
        int goodsCapacity = bogies.stream()
                .filter(bogie -> "Goods".equals(bogie.getType()))
                .map(Bogie::getCapacity)
                .reduce(0, Integer::sum);
        System.out.println("Total goods loading capacity: " + goodsCapacity + " units\n");

        // Calculate 4: Average capacity per bogie
        System.out.println("Aggregation 4: Average Capacity Per Bogie");
        System.out.println("-----------------------------------------");
        double averageCapacity = bogies.stream()
                .map(Bogie::getCapacity)
                .reduce(0, Integer::sum) / (double) bogies.size();
        System.out.println("Average capacity per bogie: " + String.format("%.2f", averageCapacity) + " seats\n");

        // Calculate 5: Maximum and minimum capacity
        System.out.println("Aggregation 5: Capacity Statistics");
        System.out.println("----------------------------------");
        int maxCapacity = bogies.stream()
                .map(Bogie::getCapacity)
                .reduce(0, Integer::max);
        int minCapacity = bogies.stream()
                .map(Bogie::getCapacity)
                .reduce(Integer.MAX_VALUE, Integer::min);
        System.out.println("Maximum capacity bogie: " + maxCapacity + " seats");
        System.out.println("Minimum capacity bogie: " + minCapacity + " seats\n");

        // Display summary
        System.out.println("=== Train Capacity Summary ===");
        System.out.println("Total bogies: " + bogies.size());
        System.out.println("Total capacity: " + totalCapacity + " seats");
        System.out.println("Passenger capacity: " + passengerCapacity + " seats");
        System.out.println("Goods capacity: " + goodsCapacity + " units");
        System.out.println("Average capacity: " + String.format("%.2f", averageCapacity) + " seats/bogie");

        // Verify original list integrity
        System.out.println("\n=== Verify Original Collection Integrity ===");
        System.out.println("Original collection size: " + bogies.size());
        System.out.println("Original bogies unchanged: " + (bogies.size() == 7));

        System.out.println("\n=== Stream Reduction Complete ===");
    }
}
