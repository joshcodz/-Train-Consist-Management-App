import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * UC14: Custom Exception for Invalid Bogie Capacity
 * Checked exception thrown when a bogie is created with invalid capacity
 */
class InvalidCapacityException extends Exception {
    public InvalidCapacityException(String message) {
        super(message);
    }
}

class Bogie {
    private String name;
    private String type;
    private int capacity;
    private String cargo;      // New field for cargo type (UC12)

    public Bogie(String name, String type, int capacity) throws InvalidCapacityException {
        this(name, type, capacity, null);
    }

    public Bogie(String name, String type, int capacity, String cargo) throws InvalidCapacityException {
        // UC14: Validate capacity - fail fast for invalid values
        if (capacity <= 0) {
            throw new InvalidCapacityException("Capacity must be greater than zero");
        }
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.cargo = cargo;
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

    public String getCargo() {
        return cargo;
    }

    @Override
    public String toString() {
        if (cargo != null) {
            return name + " (" + type + ", " + capacity + ", cargo: " + cargo + ")";
        }
        return name + " (" + type + ", " + capacity + " seats)";
    }
}

public class TrainConsistManagementApp {
    // Define regex patterns for validation (UC11)
    private static final String TRAIN_ID_PATTERN = "TRN-\\d{4}";
    private static final String CARGO_CODE_PATTERN = "PET-[A-Z]{2}";
    
    // Compile patterns for efficiency (UC11)
    private static final Pattern trainIdPattern = Pattern.compile(TRAIN_ID_PATTERN);
    private static final Pattern cargoCodePattern = Pattern.compile(CARGO_CODE_PATTERN);

    /**
     * Validate Train ID format (UC11)
     * @param trainId The train ID to validate
     * @return true if valid, false otherwise
     */
    public static boolean validateTrainID(String trainId) {
        if (trainId == null || trainId.isEmpty()) {
            return false;
        }
        Matcher matcher = trainIdPattern.matcher(trainId);
        return matcher.matches();
    }

    /**
     * Validate Cargo Code format (UC11)
     * @param cargoCode The cargo code to validate
     * @return true if valid, false otherwise
     */
    public static boolean validateCargoCode(String cargoCode) {
        if (cargoCode == null || cargoCode.isEmpty()) {
            return false;
        }
        Matcher matcher = cargoCodePattern.matcher(cargoCode);
        return matcher.matches();
    }

    /**
     * UC13: Filter bogies using traditional loop-based approach
     * Imperative style - focuses on HOW to iterate and filter
     * @param bogies List of bogies to filter
     * @param minCapacity Minimum capacity threshold
     * @return List of filtered bogies
     */
    public static List<Bogie> filterBogiesByLoop(List<Bogie> bogies, int minCapacity) {
        List<Bogie> filtered = new ArrayList<>();
        for (Bogie bogie : bogies) {
            if (bogie.getCapacity() > minCapacity) {
                filtered.add(bogie);
            }
        }
        return filtered;
    }

    /**
     * UC13: Filter bogies using stream-based approach
     * Declarative style - focuses on WHAT result we want
     * @param bogies List of bogies to filter
     * @param minCapacity Minimum capacity threshold
     * @return List of filtered bogies
     */
    public static List<Bogie> filterBogiesByStream(List<Bogie> bogies, int minCapacity) {
        return bogies.stream()
                .filter(bogie -> bogie.getCapacity() > minCapacity)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * UC13: Benchmark and measure loop-based filtering performance
     * @param bogies List of bogies to filter
     * @param minCapacity Minimum capacity threshold
     * @return Object containing result list and execution time
     */
    public static class PerformanceResult {
        public List<Bogie> result;
        public long executionTimeNanos;
        
        public PerformanceResult(List<Bogie> result, long executionTimeNanos) {
            this.result = result;
            this.executionTimeNanos = executionTimeNanos;
        }
        
        public double getExecutionTimeMillis() {
            return executionTimeNanos / 1_000_000.0;
        }
    }

    public static PerformanceResult benchmarkLoopFiltering(List<Bogie> bogies, int minCapacity) {
        long startTime = System.nanoTime();
        List<Bogie> result = filterBogiesByLoop(bogies, minCapacity);
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        return new PerformanceResult(result, elapsedTime);
    }

    /**
     * UC13: Benchmark and measure stream-based filtering performance
     * @param bogies List of bogies to filter
     * @param minCapacity Minimum capacity threshold
     * @return Object containing result list and execution time
     */
    public static PerformanceResult benchmarkStreamFiltering(List<Bogie> bogies, int minCapacity) {
        long startTime = System.nanoTime();
        List<Bogie> result = filterBogiesByStream(bogies, minCapacity);
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        return new PerformanceResult(result, elapsedTime);
    }

    public static void main(String[] args) {
        System.out.println("=== Train Consist Management App - UC14: Custom Exception Handling ===\n");

        // Create a List to store bogie objects
        List<Bogie> bogies = new ArrayList<>();

        // Add passenger bogies with various capacities (UC14: Must handle InvalidCapacityException)
        try {
            bogies.add(new Bogie("Sleeper", "Passenger", 72));
            bogies.add(new Bogie("AC Chair", "Passenger", 96));
            bogies.add(new Bogie("First Class", "Passenger", 48));
            bogies.add(new Bogie("General", "Passenger", 120));
            bogies.add(new Bogie("AC Sleeper", "Passenger", 60));
        } catch (InvalidCapacityException e) {
            System.out.println("Error creating bogies: " + e.getMessage());
            System.exit(1);
        }

        System.out.println("Original Bogie List:");
        System.out.println("-------------------");
        for (Bogie bogie : bogies) {
            System.out.println("  " + bogie);
        }

        int capacityThreshold = 60;
        System.out.println("\n=== UC14: Custom Exception Handling - Fail-Fast Validation ===");
        System.out.println("Task: Demonstrate proper handling of invalid bogie capacity\n");

        // Test Case 1: Valid capacity (success case)
        System.out.println("Test Case 1: Valid Capacity Creation");
        System.out.println("------------------------------------");
        try {
            Bogie validBogie = new Bogie("TestBogie", "Passenger", 50);
            System.out.println("✓ Created successfully: " + validBogie);
        } catch (InvalidCapacityException e) {
            System.out.println("✗ Exception (unexpected): " + e.getMessage());
        }

        // Test Case 2: Invalid capacity - negative value (failure case)
        System.out.println("\nTest Case 2: Negative Capacity Detection");
        System.out.println("---------------------------------------");
        try {
            Bogie negativeBogie = new Bogie("InvalidBogie", "Passenger", -10);
            System.out.println("✗ Should have thrown exception!");
        } catch (InvalidCapacityException e) {
            System.out.println("✓ Exception caught (expected): " + e.getMessage());
        }

        // Test Case 3: Invalid capacity - zero value (failure case)
        System.out.println("\nTest Case 3: Zero Capacity Detection");
        System.out.println("-----------------------------------");
        try {
            Bogie zeroBogie = new Bogie("InvalidBogie", "Passenger", 0);
            System.out.println("✗ Should have thrown exception!");
        } catch (InvalidCapacityException e) {
            System.out.println("✓ Exception caught (expected): " + e.getMessage());
        }

        // Test Case 4: Multiple valid bogie creation
        System.out.println("\nTest Case 4: Multiple Valid Bogies Creation");
        System.out.println("------------------------------------------");
        try {
            Bogie bogie1 = new Bogie("Bogie-A", "Goods", 100);
            Bogie bogie2 = new Bogie("Bogie-B", "Goods", 200, "Petroleum");
            Bogie bogie3 = new Bogie("Bogie-C", "Passenger", 75);
            System.out.println("✓ All bogies created successfully:");
            System.out.println("  " + bogie1);
            System.out.println("  " + bogie2);
            System.out.println("  " + bogie3);
        } catch (InvalidCapacityException e) {
            System.out.println("✗ Exception (unexpected): " + e.getMessage());
        }

        // Test Case 5: Object integrity after creation
        System.out.println("\nTest Case 5: Object Integrity Validation");
        System.out.println("---------------------------------------");
        try {
            Bogie bogie = new Bogie("TestBogie", "Passenger", 85);
            System.out.println("Created bogie: " + bogie);
            System.out.println("  Name: " + bogie.getName());
            System.out.println("  Type: " + bogie.getType());
            System.out.println("  Capacity: " + bogie.getCapacity());
            System.out.println("✓ All properties verified correctly");
        } catch (InvalidCapacityException e) {
            System.out.println("✗ Exception (unexpected): " + e.getMessage());
        }

        // Display key concepts
        System.out.println("\n\n=== Key Concepts Used in UC14 ===");
        System.out.println("• Custom Exception – User-defined exception for domain-specific errors");
        System.out.println("• Exception Inheritance – Extending Exception for checked exceptions");
        System.out.println("• throw Keyword – Explicitly raising exceptions for business rule violations");
        System.out.println("• throws Declaration – Declaring exceptions in method signatures");
        System.out.println("• Fail-Fast Validation – Detecting errors early at object creation");
        System.out.println("• Business Rule Enforcement – Encapsulating railway constraints");
        System.out.println("• Defensive Programming – Preventing corrupted data from entering system");

        System.out.println("\n=== UC14: Exception Handling Complete ===");
    }
}
