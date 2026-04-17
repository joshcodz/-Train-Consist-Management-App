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
     * UC12: Check safety compliance for goods bogies
     * Enforces business rule: Cylindrical bogies must carry only Petroleum
     * Uses allMatch() to validate all bogies in the stream
     * @param goodsBogies List of goods bogies to validate
     * @return true if all bogies comply with safety rules, false otherwise
     */
    public static boolean checkSafetyCompliance(List<Bogie> goodsBogies) {
        if (goodsBogies == null || goodsBogies.isEmpty()) {
            return true;  // No bogies = no violations
        }
        
        return goodsBogies.stream()
                .allMatch(bogie -> {
                    // Safety Rule: Cylindrical bogies must carry only Petroleum
                    if (bogie.getName() != null && bogie.getName().startsWith("Cylindrical")) {
                        return "Petroleum".equals(bogie.getCargo());
                    }
                    // Other bogie types (Open, Box, etc.) can carry any cargo
                    return true;
                });
    }

    public static void main(String[] args) {
        System.out.println("=== Train Consist Management App - UC12: Safety Compliance Check ===\n");

        // Create a List to store bogie objects
        List<Bogie> bogies = new ArrayList<>();

        // Add passenger bogies with their types and capacities
        bogies.add(new Bogie("Sleeper", "Passenger", 72));
        bogies.add(new Bogie("AC Chair", "Passenger", 96));
        bogies.add(new Bogie("First Class", "Passenger", 48));
        bogies.add(new Bogie("General", "Passenger", 120));
        bogies.add(new Bogie("AC Sleeper", "Passenger", 60));

        System.out.println("Original Bogie List (Mixed Passenger & Goods):");
        System.out.println("-------------------------------------------");
        for (Bogie bogie : bogies) {
            System.out.println("  " + bogie);
        }

        // UC12: Safety Compliance Check for Goods Bogies
        System.out.println("\n=== UC12: Safety Compliance Check Using allMatch() ===\n");

        // Test Case 1: All valid cylindrical bogies with Petroleum
        System.out.println("Test Case 1: Valid Train Formation (All Cylindrical with Petroleum)");
        System.out.println("--------------------------------------------------------------");
        List<Bogie> validTrain = new ArrayList<>();
        validTrain.add(new Bogie("Cylindrical-A", "Goods", 500, "Petroleum"));
        validTrain.add(new Bogie("Cylindrical-B", "Goods", 500, "Petroleum"));
        validTrain.add(new Bogie("Box-A", "Goods", 400, "Coal"));
        
        System.out.println("Goods Bogies:");
        for (Bogie bogie : validTrain) {
            System.out.println("  " + bogie);
        }
        
        boolean isSafe1 = checkSafetyCompliance(validTrain);
        System.out.println("Safety Compliance Result: " + (isSafe1 ? "✓ SAFE" : "✗ UNSAFE"));

        // Test Case 2: Cylindrical bogie with wrong cargo (violates rule)
        System.out.println("\nTest Case 2: Invalid Train Formation (Cylindrical with Coal)");
        System.out.println("-----------------------------------------------------");
        List<Bogie> invalidTrain = new ArrayList<>();
        invalidTrain.add(new Bogie("Cylindrical-A", "Goods", 500, "Petroleum"));
        invalidTrain.add(new Bogie("Cylindrical-B", "Goods", 500, "Coal"));  // VIOLATION!
        invalidTrain.add(new Bogie("Open-A", "Goods", 600, "Grain"));
        
        System.out.println("Goods Bogies:");
        for (Bogie bogie : invalidTrain) {
            System.out.println("  " + bogie);
        }
        
        boolean isSafe2 = checkSafetyCompliance(invalidTrain);
        System.out.println("Safety Compliance Result: " + (isSafe2 ? "✓ SAFE" : "✗ UNSAFE"));

        // Test Case 3: Non-cylindrical bogies with flexible cargo
        System.out.println("\nTest Case 3: Non-Cylindrical Bogies (Flexible Cargo Rules)");
        System.out.println("------------------------------------------------");
        List<Bogie> flexibleTrain = new ArrayList<>();
        flexibleTrain.add(new Bogie("Open-A", "Goods", 600, "Coal"));
        flexibleTrain.add(new Bogie("Box-A", "Goods", 400, "Grain"));
        flexibleTrain.add(new Bogie("Box-B", "Goods", 400, "Petroleum"));
        
        System.out.println("Goods Bogies:");
        for (Bogie bogie : flexibleTrain) {
            System.out.println("  " + bogie);
        }
        
        boolean isSafe3 = checkSafetyCompliance(flexibleTrain);
        System.out.println("Safety Compliance Result: " + (isSafe3 ? "✓ SAFE" : "✗ UNSAFE"));

        // Test Case 4: Mixed bogies with one violation
        System.out.println("\nTest Case 4: Mixed Bogie Types with One Violation");
        System.out.println("----------------------------------------------");
        List<Bogie> mixedTrain = new ArrayList<>();
        mixedTrain.add(new Bogie("Cylindrical-A", "Goods", 500, "Petroleum"));
        mixedTrain.add(new Bogie("Open-A", "Goods", 600, "Coal"));
        mixedTrain.add(new Bogie("Cylindrical-B", "Goods", 500, "Grain"));  // VIOLATION!
        mixedTrain.add(new Bogie("Box-A", "Goods", 400, "Coal"));
        
        System.out.println("Goods Bogies:");
        for (Bogie bogie : mixedTrain) {
            System.out.println("  " + bogie);
        }
        
        boolean isSafe4 = checkSafetyCompliance(mixedTrain);
        System.out.println("Safety Compliance Result: " + (isSafe4 ? "✓ SAFE" : "✗ UNSAFE"));

        // Test Case 5: Empty bogie list
        System.out.println("\nTest Case 5: Empty Goods Bogie List");
        System.out.println("---------------------------------");
        List<Bogie> emptyTrain = new ArrayList<>();
        System.out.println("Goods Bogies: (none)");
        
        boolean isSafe5 = checkSafetyCompliance(emptyTrain);
        System.out.println("Safety Compliance Result: " + (isSafe5 ? "✓ SAFE" : "✗ UNSAFE"));

        // Display key concepts
        System.out.println("\n=== Key Concepts Used in UC12 ===");
        System.out.println("• Streams API – Declarative data processing");
        System.out.println("• allMatch() – Validates all elements satisfy a condition");
        System.out.println("• Lambda Expressions – Inline validation rules");
        System.out.println("• Conditional Logic – Enforce domain-specific constraints");
        System.out.println("• Short-Circuit Evaluation – Stops when first violation found");
        System.out.println("• Business Rule Modeling – Real-world safety policies as code");

        System.out.println("\n=== Safety Compliance Check Complete ===");
    }
}
