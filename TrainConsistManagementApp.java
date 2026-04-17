import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    // Define regex patterns for validation
    private static final String TRAIN_ID_PATTERN = "TRN-\\d{4}";
    private static final String CARGO_CODE_PATTERN = "PET-[A-Z]{2}";
    
    // Compile patterns for efficiency
    private static final Pattern trainIdPattern = Pattern.compile(TRAIN_ID_PATTERN);
    private static final Pattern cargoCodePattern = Pattern.compile(CARGO_CODE_PATTERN);

    /**
     * Validate Train ID format
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
     * Validate Cargo Code format
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

    public static void main(String[] args) {
        System.out.println("=== Train Consist Management App - UC11: Validate Train ID & Cargo Codes ===\n");

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

        // UC11: Regex Validation Examples
        System.out.println("\n=== UC11: Regex Validation Examples ===\n");

        // Test case 1: Valid Train ID
        System.out.println("Validation 1: Train ID Format");
        System.out.println("-----------------------------");
        String[] trainIds = {"TRN-1234", "TRAIN12", "TRN12A", "1234-TRN", "TRN-123", "TRN-12345"};
        for (String trainId : trainIds) {
            boolean isValid = validateTrainID(trainId);
            System.out.println("Train ID: " + trainId + " -> " + (isValid ? "✓ VALID" : "✗ INVALID"));
        }

        // Test case 2: Valid Cargo Code
        System.out.println("\nValidation 2: Cargo Code Format");
        System.out.println("-------------------------------");
        String[] cargoCodes = {"PET-AB", "PET-ab", "PET123", "AB-PET", "PET-A", "PET-ABC"};
        for (String cargoCode : cargoCodes) {
            boolean isValid = validateCargoCode(cargoCode);
            System.out.println("Cargo Code: " + cargoCode + " -> " + (isValid ? "✓ VALID" : "✗ INVALID"));
        }

        // Test case 3: Empty input handling
        System.out.println("\nValidation 3: Empty Input Handling");
        System.out.println("----------------------------------");
        String emptyTrainId = "";
        String emptyCargoCode = "";
        System.out.println("Empty Train ID: '" + emptyTrainId + "' -> " + (validateTrainID(emptyTrainId) ? "✓ VALID" : "✗ INVALID"));
        System.out.println("Empty Cargo Code: '" + emptyCargoCode + "' -> " + (validateCargoCode(emptyCargoCode) ? "✓ VALID" : "✗ INVALID"));

        // Test case 4: Null input handling
        System.out.println("\nValidation 4: Null Input Handling");
        System.out.println("---------------------------------");
        System.out.println("Null Train ID -> " + (validateTrainID(null) ? "✓ VALID" : "✗ INVALID"));
        System.out.println("Null Cargo Code -> " + (validateCargoCode(null) ? "✓ VALID" : "✗ INVALID"));

        // Test case 5: Real-world scenario
        System.out.println("\nValidation 5: Real-World Scenario");
        System.out.println("--------------------------------");
        String trainId = "TRN-5678";
        String cargoCode = "PET-CD";
        System.out.println("Train ID: " + trainId + " -> " + (validateTrainID(trainId) ? "✓ VALID" : "✗ INVALID"));
        System.out.println("Cargo Code: " + cargoCode + " -> " + (validateCargoCode(cargoCode) ? "✓ VALID" : "✗ INVALID"));

        if (validateTrainID(trainId) && validateCargoCode(cargoCode)) {
            System.out.println("✓ Train " + trainId + " with cargo " + cargoCode + " is properly formatted and ready for processing.");
        }

        // Display regex patterns for reference
        System.out.println("\n=== Regex Patterns Used ===");
        System.out.println("Train ID Pattern: " + TRAIN_ID_PATTERN);
        System.out.println("Cargo Code Pattern: " + CARGO_CODE_PATTERN);

        System.out.println("\n=== Regex Validation Complete ===");
    }
}
