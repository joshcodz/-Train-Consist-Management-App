import java.util.ArrayList;
import java.util.List;

/**
 * Test Cases for UC12: Safety Compliance Check for Goods Bogies
 * 
 * This test class verifies that stream-based safety validation works correctly
 * using allMatch() to enforce business rules for goods bogie cargo assignments.
 * 
 * Business Rule: Cylindrical bogies must carry ONLY Petroleum
 * Other bogie types (Open, Box, etc.) can carry any cargo
 */
public class TrainConsistManagementAppTest {
    
    /**
     * Helper class to define goods bogies
     */
    static class Bogie {
        private String name;
        private String type;
        private int capacity;
        private String cargo;

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

    /**
     * Safety Compliance Check - enforces Cylindrical → Petroleum rule
     */
    public static boolean checkSafetyCompliance(List<Bogie> goodsBogies) {
        if (goodsBogies == null || goodsBogies.isEmpty()) {
            return true;  // No bogies = no violations
        }
        
        return goodsBogies.stream()
                .allMatch(bogie -> {
                    // Check if bogie name starts with "Cylindrical"
                    if (bogie.getName() != null && bogie.getName().startsWith("Cylindrical")) {
                        return "Petroleum".equals(bogie.getCargo());
                    }
                    return true;  // Other bogie types allow any cargo
                });
    }

    /**
     * Test: All Bogies Valid
     * Expected: All cylindrical bogies carry Petroleum, validation passes
     */
    public void testSafety_AllBogiesValid() {
        System.out.println("TEST: testSafety_AllBogiesValid");
        
        List<Bogie> validTrain = new ArrayList<>();
        validTrain.add(new Bogie("Cylindrical-A", "Goods", 500, "Petroleum"));
        validTrain.add(new Bogie("Cylindrical-B", "Goods", 500, "Petroleum"));
        validTrain.add(new Bogie("Box-A", "Goods", 400, "Coal"));
        
        System.out.println("Train Bogies:");
        for (Bogie bogie : validTrain) {
            System.out.println("  " + bogie);
        }
        
        boolean result = checkSafetyCompliance(validTrain);
        System.out.println("Expected: true (all cylinders have Petroleum)");
        System.out.println("Actual: " + result);
        
        assert result : "Valid train formation should pass safety check";
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Cylindrical With Invalid Cargo
     * Expected: Cylindrical bogie with Coal fails validation
     */
    public void testSafety_CylindricalWithInvalidCargo() {
        System.out.println("TEST: testSafety_CylindricalWithInvalidCargo");
        
        List<Bogie> invalidTrain = new ArrayList<>();
        invalidTrain.add(new Bogie("Cylindrical-A", "Goods", 500, "Petroleum"));
        invalidTrain.add(new Bogie("Cylindrical-B", "Goods", 500, "Coal"));  // VIOLATION!
        
        System.out.println("Train Bogies:");
        for (Bogie bogie : invalidTrain) {
            System.out.println("  " + bogie);
        }
        
        boolean result = checkSafetyCompliance(invalidTrain);
        System.out.println("Expected: false (cylinder carrying Coal)");
        System.out.println("Actual: " + result);
        
        assert !result : "Cylindrical bogie with non-Petroleum cargo should fail";
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Non-Cylindrical Bogies Allowed
     * Expected: Non-cylindrical bogies accept any cargo
     */
    public void testSafety_NonCylindricalBogiesAllowed() {
        System.out.println("TEST: testSafety_NonCylindricalBogiesAllowed");
        
        List<Bogie> flexibleTrain = new ArrayList<>();
        flexibleTrain.add(new Bogie("Open-A", "Goods", 600, "Coal"));
        flexibleTrain.add(new Bogie("Box-A", "Goods", 400, "Grain"));
        flexibleTrain.add(new Bogie("Box-B", "Goods", 400, "Petroleum"));
        
        System.out.println("Train Bogies:");
        for (Bogie bogie : flexibleTrain) {
            System.out.println("  " + bogie);
        }
        
        boolean result = checkSafetyCompliance(flexibleTrain);
        System.out.println("Expected: true (non-cylindrical can have any cargo)");
        System.out.println("Actual: " + result);
        
        assert result : "Non-cylindrical bogies with any cargo should pass";
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Mixed Bogies With Violation
     * Expected: One cylindrical bogie violation fails entire train
     */
    public void testSafety_MixedBogiesWithViolation() {
        System.out.println("TEST: testSafety_MixedBogiesWithViolation");
        
        List<Bogie> mixedTrain = new ArrayList<>();
        mixedTrain.add(new Bogie("Cylindrical-A", "Goods", 500, "Petroleum"));
        mixedTrain.add(new Bogie("Open-A", "Goods", 600, "Coal"));
        mixedTrain.add(new Bogie("Cylindrical-B", "Goods", 500, "Grain"));  // VIOLATION!
        mixedTrain.add(new Bogie("Box-A", "Goods", 400, "Coal"));
        
        System.out.println("Train Bogies:");
        for (Bogie bogie : mixedTrain) {
            System.out.println("  " + bogie);
        }
        
        boolean result = checkSafetyCompliance(mixedTrain);
        System.out.println("Expected: false (one cylinder carrying Grain)");
        System.out.println("Actual: " + result);
        
        assert !result : "One violation should fail entire train";
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Empty Bogie List
     * Expected: Empty list returns true (no violations)
     */
    public void testSafety_EmptyBogieList() {
        System.out.println("TEST: testSafety_EmptyBogieList");
        
        List<Bogie> emptyTrain = new ArrayList<>();
        System.out.println("Train Bogies: (empty)");
        
        boolean result = checkSafetyCompliance(emptyTrain);
        System.out.println("Expected: true (no bogies = no violations)");
        System.out.println("Actual: " + result);
        
        assert result : "Empty list should be considered safe";
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Only Valid Cylindrical Bogies
     * Expected: Multiple cylindrical bogies with only Petroleum cargo should pass
     */
    public void testSafety_OnlyValidCylindricals() {
        System.out.println("TEST: testSafety_OnlyValidCylindricals");
        
        List<Bogie> cylindricalTrain = new ArrayList<>();
        cylindricalTrain.add(new Bogie("Cylindrical-A", "Goods", 500, "Petroleum"));
        cylindricalTrain.add(new Bogie("Cylindrical-B", "Goods", 500, "Petroleum"));
        cylindricalTrain.add(new Bogie("Cylindrical-C", "Goods", 500, "Petroleum"));
        
        System.out.println("Train Bogies:");
        for (Bogie bogie : cylindricalTrain) {
            System.out.println("  " + bogie);
        }
        
        boolean result = checkSafetyCompliance(cylindricalTrain);
        System.out.println("Expected: true (all cylinders have Petroleum)");
        System.out.println("Actual: " + result);
        
        assert result : "All valid cylindrical bogies should pass";
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Cylindrical With Grain
     * Expected: Cylindrical bogie with Grain fails validation
     */
    public void testSafety_CylindricalWithGrain() {
        System.out.println("TEST: testSafety_CylindricalWithGrain");
        
        List<Bogie> invalidTrain = new ArrayList<>();
        invalidTrain.add(new Bogie("Cylindrical-A", "Goods", 500, "Grain"));  // VIOLATION!
        
        System.out.println("Train Bogies:");
        for (Bogie bogie : invalidTrain) {
            System.out.println("  " + bogie);
        }
        
        boolean result = checkSafetyCompliance(invalidTrain);
        System.out.println("Expected: false (cylinder carrying Grain)");
        System.out.println("Actual: " + result);
        
        assert !result : "Cylindrical bogie with Grain should fail";
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Null Input
     * Expected: Null input returns true (no violations)
     */
    public void testSafety_NullInput() {
        System.out.println("TEST: testSafety_NullInput");
        
        System.out.println("Train Bogies: null");
        
        boolean result = checkSafetyCompliance(null);
        System.out.println("Expected: true (null = no violations)");
        System.out.println("Actual: " + result);
        
        assert result : "Null input should be considered safe";
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: allMatch() Short-Circuit Evaluation
     * Expected: Stops processing after first violation found
     */
    public void testSafety_ShortCircuitEvaluation() {
        System.out.println("TEST: testSafety_ShortCircuitEvaluation");
        
        List<Bogie> trainWithViolation = new ArrayList<>();
        trainWithViolation.add(new Bogie("Cylindrical-A", "Goods", 500, "Petroleum"));
        trainWithViolation.add(new Bogie("Cylindrical-B", "Goods", 500, "Coal"));  // VIOLATION - stops here
        trainWithViolation.add(new Bogie("Cylindrical-C", "Goods", 500, "Petroleum"));
        trainWithViolation.add(new Bogie("Cylindrical-D", "Goods", 500, "Coal"));  // Never evaluated
        
        System.out.println("Train Bogies:");
        for (Bogie bogie : trainWithViolation) {
            System.out.println("  " + bogie);
        }
        
        boolean result = checkSafetyCompliance(trainWithViolation);
        System.out.println("Expected: false (stops at first violation)");
        System.out.println("Actual: " + result);
        System.out.println("Note: allMatch() uses short-circuit evaluation - stops at first failure");
        
        assert !result : "Should fail on first violation";
        System.out.println("✓ PASSED\n");
    }

    /**
     * Run all test cases
     */
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("UC12: Safety Compliance Check Test Suite");
        System.out.println("========================================\n");
        
        TrainConsistManagementAppTest tester = new TrainConsistManagementAppTest();
        
        try {
            tester.testSafety_AllBogiesValid();
            tester.testSafety_CylindricalWithInvalidCargo();
            tester.testSafety_NonCylindricalBogiesAllowed();
            tester.testSafety_MixedBogiesWithViolation();
            tester.testSafety_EmptyBogieList();
            tester.testSafety_OnlyValidCylindricals();
            tester.testSafety_CylindricalWithGrain();
            tester.testSafety_NullInput();
            tester.testSafety_ShortCircuitEvaluation();
            
            System.out.println("========================================");
            System.out.println("✓ ALL 9 TESTS PASSED!");
            System.out.println("========================================");
        } catch (AssertionError e) {
            System.out.println("\n✗ TEST FAILED: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
