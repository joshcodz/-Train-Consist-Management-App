import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
        System.out.println("=== Train Consist Management App - UC7: Sort Bogies by Capacity ===\n");

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

        // Sort bogies by capacity in ascending order using Comparator.comparingInt()
        System.out.println("\nSorting bogies by capacity (ascending)...\n");
        Collections.sort(bogies, Comparator.comparingInt(Bogie::getCapacity));

        System.out.println("Sorted Bogie List (by capacity):");
        System.out.println("--------------------------------");
        for (Bogie bogie : bogies) {
            System.out.println("  " + bogie);
        }

        // Sort in descending order using reversed()
        System.out.println("\nSorting bogies by capacity (descending)...\n");
        Collections.sort(bogies, Comparator.comparingInt(Bogie::getCapacity).reversed());

        System.out.println("Sorted Bogie List (descending by capacity):");
        System.out.println("------------------------------------------");
        for (Bogie bogie : bogies) {
            System.out.println("  " + bogie);
        }

        System.out.println("\nTotal passenger bogies in formation: " + bogies.size());
    }
}
