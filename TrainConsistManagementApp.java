import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Bogie {
    private String name;
    private String type;
    private int capacity;
    private String cargo;      // New field for cargo type (UC12)

    public Bogie(String name, String type, int capacity) {
        this(name, type, capacity, null);
    }

    public Bogie(String name, String type, int capacity, String cargo) {
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
        System.out.println("=== Train Consist Management App - UC13: Performance Comparison ===\n");

        // Create a List to store bogie objects
        List<Bogie> bogies = new ArrayList<>();

        // Add passenger bogies with various capacities
        bogies.add(new Bogie("Sleeper", "Passenger", 72));
        bogies.add(new Bogie("AC Chair", "Passenger", 96));
        bogies.add(new Bogie("First Class", "Passenger", 48));
        bogies.add(new Bogie("General", "Passenger", 120));
        bogies.add(new Bogie("AC Sleeper", "Passenger", 60));

        System.out.println("Original Bogie List:");
        System.out.println("-------------------");
        for (Bogie bogie : bogies) {
            System.out.println("  " + bogie);
        }

        int capacityThreshold = 60;
        System.out.println("\n=== UC13: Performance Comparison (Loops vs Streams) ===");
        System.out.println("Task: Filter bogies with capacity > " + capacityThreshold + "\n");

        // Test Case 1: Small dataset comparison
        System.out.println("Test Case 1: Small Dataset (5 bogies)");
        System.out.println("------------------------------------");
        
        PerformanceResult loopResult = benchmarkLoopFiltering(bogies, capacityThreshold);
        System.out.println("Loop-Based Filtering:");
        System.out.println("  Time (ns): " + loopResult.executionTimeNanos);
        System.out.println("  Time (ms): " + String.format("%.6f", loopResult.getExecutionTimeMillis()));
        System.out.println("  Results: " + loopResult.result.size() + " bogies filtered");
        for (Bogie bogie : loopResult.result) {
            System.out.println("    " + bogie);
        }
        
        PerformanceResult streamResult = benchmarkStreamFiltering(bogies, capacityThreshold);
        System.out.println("\nStream-Based Filtering:");
        System.out.println("  Time (ns): " + streamResult.executionTimeNanos);
        System.out.println("  Time (ms): " + String.format("%.6f", streamResult.getExecutionTimeMillis()));
        System.out.println("  Results: " + streamResult.result.size() + " bogies filtered");
        for (Bogie bogie : streamResult.result) {
            System.out.println("    " + bogie);
        }
        
        long loopSmallTime = loopResult.executionTimeNanos;
        long streamSmallTime = streamResult.executionTimeNanos;
        System.out.println("\nSmall Dataset Comparison:");
        System.out.println("  Loop time: " + loopSmallTime + " ns");
        System.out.println("  Stream time: " + streamSmallTime + " ns");
        System.out.println("  Difference: " + Math.abs(loopSmallTime - streamSmallTime) + " ns");
        System.out.println("  Results match: " + (loopResult.result.size() == streamResult.result.size()));

        // Test Case 2: Medium dataset comparison
        System.out.println("\n\nTest Case 2: Medium Dataset (1,000 bogies)");
        System.out.println("------------------------------------------");
        List<Bogie> mediumDataset = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            mediumDataset.add(new Bogie("Bogie-" + i, "Goods", 40 + (i % 100)));
        }
        
        PerformanceResult loopMedium = benchmarkLoopFiltering(mediumDataset, capacityThreshold);
        System.out.println("Loop-Based Filtering:");
        System.out.println("  Time (ns): " + loopMedium.executionTimeNanos);
        System.out.println("  Time (ms): " + String.format("%.6f", loopMedium.getExecutionTimeMillis()));
        System.out.println("  Results: " + loopMedium.result.size() + " bogies filtered");
        
        PerformanceResult streamMedium = benchmarkStreamFiltering(mediumDataset, capacityThreshold);
        System.out.println("\nStream-Based Filtering:");
        System.out.println("  Time (ns): " + streamMedium.executionTimeNanos);
        System.out.println("  Time (ms): " + String.format("%.6f", streamMedium.getExecutionTimeMillis()));
        System.out.println("  Results: " + streamMedium.result.size() + " bogies filtered");
        
        long loopMediumTime = loopMedium.executionTimeNanos;
        long streamMediumTime = streamMedium.executionTimeNanos;
        System.out.println("\nMedium Dataset Comparison:");
        System.out.println("  Loop time: " + loopMediumTime + " ns");
        System.out.println("  Stream time: " + streamMediumTime + " ns");
        System.out.println("  Difference: " + Math.abs(loopMediumTime - streamMediumTime) + " ns");
        System.out.println("  Results match: " + (loopMedium.result.size() == streamMedium.result.size()));

        // Test Case 3: Large dataset comparison
        System.out.println("\n\nTest Case 3: Large Dataset (10,000 bogies)");
        System.out.println("-----------------------------------------");
        List<Bogie> largeDataset = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            largeDataset.add(new Bogie("Bogie-" + i, "Goods", 40 + (i % 100)));
        }
        
        PerformanceResult loopLarge = benchmarkLoopFiltering(largeDataset, capacityThreshold);
        System.out.println("Loop-Based Filtering:");
        System.out.println("  Time (ns): " + loopLarge.executionTimeNanos);
        System.out.println("  Time (ms): " + String.format("%.6f", loopLarge.getExecutionTimeMillis()));
        System.out.println("  Results: " + loopLarge.result.size() + " bogies filtered");
        
        PerformanceResult streamLarge = benchmarkStreamFiltering(largeDataset, capacityThreshold);
        System.out.println("\nStream-Based Filtering:");
        System.out.println("  Time (ns): " + streamLarge.executionTimeNanos);
        System.out.println("  Time (ms): " + String.format("%.6f", streamLarge.getExecutionTimeMillis()));
        System.out.println("  Results: " + streamLarge.result.size() + " bogies filtered");
        
        long loopLargeTime = loopLarge.executionTimeNanos;
        long streamLargeTime = streamLarge.executionTimeNanos;
        System.out.println("\nLarge Dataset Comparison:");
        System.out.println("  Loop time: " + loopLargeTime + " ns");
        System.out.println("  Stream time: " + streamLargeTime + " ns");
        System.out.println("  Difference: " + Math.abs(loopLargeTime - streamLargeTime) + " ns");
        System.out.println("  Results match: " + (loopLarge.result.size() == streamLarge.result.size()));

        // Display key concepts
        System.out.println("\n\n=== Key Concepts Used in UC13 ===");
        System.out.println("• System.nanoTime() – High-resolution time measurement");
        System.out.println("• Performance Benchmarking – Evaluate operation execution time");
        System.out.println("• Loop-Based Processing – Traditional imperative approach");
        System.out.println("• Stream-Based Processing – Declarative functional approach");
        System.out.println("• Micro-Measurement Awareness – Precise timing for small operations");
        System.out.println("• Evidence-Driven Optimization – Decisions based on measurements");

        System.out.println("\n=== Performance Comparison Complete ===");
    }
}
