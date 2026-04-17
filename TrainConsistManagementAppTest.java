import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Test Cases for UC11: Validate Train ID & Cargo Codes (Regex)
 * 
 * This test class verifies that Regular Expression validation works correctly
 * for Train IDs and Cargo Codes following the defined format patterns.
 */
public class TrainConsistManagementAppTest {
    
    // Define regex patterns
    private static final String TRAIN_ID_PATTERN = "TRN-\\d{4}";
    private static final String CARGO_CODE_PATTERN = "PET-[A-Z]{2}";
    
    // Compiled patterns
    private static final Pattern trainIdPattern = Pattern.compile(TRAIN_ID_PATTERN);
    private static final Pattern cargoCodePattern = Pattern.compile(CARGO_CODE_PATTERN);

    /**
     * Validate Train ID
     */
    public static boolean validateTrainID(String trainId) {
        if (trainId == null || trainId.isEmpty()) {
            return false;
        }
        Matcher matcher = trainIdPattern.matcher(trainId);
        return matcher.matches();
    }

    /**
     * Validate Cargo Code
     */
    public static boolean validateCargoCode(String cargoCode) {
        if (cargoCode == null || cargoCode.isEmpty()) {
            return false;
        }
        Matcher matcher = cargoCodePattern.matcher(cargoCode);
        return matcher.matches();
    }

    /**
     * Test: Valid Train ID (TRN-1234)
     * Expected: Return true for properly formatted Train ID
     */
    public void testRegex_ValidTrainID() {
        System.out.println("TEST: testRegex_ValidTrainID");
        String trainId = "TRN-1234";
        boolean result = validateTrainID(trainId);
        
        System.out.println("Input: " + trainId);
        System.out.println("Expected: true");
        System.out.println("Actual: " + result);
        
        assert result : "Valid Train ID should be accepted";
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Invalid Train ID Formats
     * Expected: Return false for incorrectly formatted Train IDs
     */
    public void testRegex_InvalidTrainIDFormat() {
        System.out.println("TEST: testRegex_InvalidTrainIDFormat");
        String[] invalidIds = {"TRAIN12", "TRN12A", "1234-TRN", "trn-1234", "TRN_1234"};
        
        for (String trainId : invalidIds) {
            boolean result = validateTrainID(trainId);
            System.out.println("Input: " + trainId + " -> " + (result ? "VALID" : "INVALID"));
            assert !result : "Invalid Train ID " + trainId + " should be rejected";
        }
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Valid Cargo Code (PET-AB)
     * Expected: Return true for properly formatted Cargo Code
     */
    public void testRegex_ValidCargoCode() {
        System.out.println("TEST: testRegex_ValidCargoCode");
        String cargoCode = "PET-AB";
        boolean result = validateCargoCode(cargoCode);
        
        System.out.println("Input: " + cargoCode);
        System.out.println("Expected: true");
        System.out.println("Actual: " + result);
        
        assert result : "Valid Cargo Code should be accepted";
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Invalid Cargo Code Formats
     * Expected: Return false for incorrectly formatted Cargo Codes
     */
    public void testRegex_InvalidCargoCodeFormat() {
        System.out.println("TEST: testRegex_InvalidCargoCodeFormat");
        String[] invalidCodes = {"PET-ab", "PET123", "AB-PET", "pet-AB", "PET_AB"};
        
        for (String cargoCode : invalidCodes) {
            boolean result = validateCargoCode(cargoCode);
            System.out.println("Input: " + cargoCode + " -> " + (result ? "VALID" : "INVALID"));
            assert !result : "Invalid Cargo Code " + cargoCode + " should be rejected";
        }
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Train ID Digit Length Validation
     * Expected: Exactly 4 digits required
     */
    public void testRegex_TrainIDDigitLengthValidation() {
        System.out.println("TEST: testRegex_TrainIDDigitLengthValidation");
        String[] testCases = {"TRN-123", "TRN-12345"};
        
        for (String trainId : testCases) {
            boolean result = validateTrainID(trainId);
            System.out.println("Input: " + trainId + " -> " + (result ? "VALID" : "INVALID"));
            assert !result : "Train ID " + trainId + " with wrong digit count should be rejected";
        }
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Cargo Code Uppercase Validation
     * Expected: Only uppercase letters allowed
     */
    public void testRegex_CargoCodeUppercaseValidation() {
        System.out.println("TEST: testRegex_CargoCodeUppercaseValidation");
        String[] testCases = {"PET-ab", "PET-Ab", "PET-aB"};
        
        for (String cargoCode : testCases) {
            boolean result = validateCargoCode(cargoCode);
            System.out.println("Input: " + cargoCode + " -> " + (result ? "VALID" : "INVALID"));
            assert !result : "Cargo Code " + cargoCode + " with lowercase letters should be rejected";
        }
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Empty Input Handling
     * Expected: Empty strings return false
     */
    public void testRegex_EmptyInputHandling() {
        System.out.println("TEST: testRegex_EmptyInputHandling");
        String emptyTrainId = "";
        String emptyCargoCode = "";
        
        boolean trainIdResult = validateTrainID(emptyTrainId);
        boolean cargoCodeResult = validateCargoCode(emptyCargoCode);
        
        System.out.println("Empty Train ID: " + (trainIdResult ? "VALID" : "INVALID"));
        System.out.println("Empty Cargo Code: " + (cargoCodeResult ? "VALID" : "INVALID"));
        
        assert !trainIdResult : "Empty Train ID should be invalid";
        assert !cargoCodeResult : "Empty Cargo Code should be invalid";
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Exact Pattern Matching
     * Expected: Partial matches are rejected
     */
    public void testRegex_ExactPatternMatch() {
        System.out.println("TEST: testRegex_ExactPatternMatch");
        String[] testCases = {"PREFIX-TRN-1234", "TRN-1234-SUFFIX", " TRN-1234"};
        
        for (String trainId : testCases) {
            boolean result = validateTrainID(trainId);
            System.out.println("Input: '" + trainId + "' -> " + (result ? "VALID" : "INVALID"));
            assert !result : "Train ID '" + trainId + "' with extra characters should be rejected";
        }
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Null Input Handling
     * Expected: Null values return false
     */
    public void testRegex_NullInputHandling() {
        System.out.println("TEST: testRegex_NullInputHandling");
        
        boolean trainIdResult = validateTrainID(null);
        boolean cargoCodeResult = validateCargoCode(null);
        
        System.out.println("Null Train ID: " + (trainIdResult ? "VALID" : "INVALID"));
        System.out.println("Null Cargo Code: " + (cargoCodeResult ? "VALID" : "INVALID"));
        
        assert !trainIdResult : "Null Train ID should be invalid";
        assert !cargoCodeResult : "Null Cargo Code should be invalid";
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Multiple Valid Train IDs
     * Expected: Multiple valid formats are accepted
     */
    public void testRegex_MultipleValidTrainIDs() {
        System.out.println("TEST: testRegex_MultipleValidTrainIDs");
        String[] validIds = {"TRN-0000", "TRN-1234", "TRN-5678", "TRN-9999"};
        
        for (String trainId : validIds) {
            boolean result = validateTrainID(trainId);
            System.out.println("Input: " + trainId + " -> " + (result ? "VALID" : "INVALID"));
            assert result : "Valid Train ID " + trainId + " should be accepted";
        }
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Multiple Valid Cargo Codes
     * Expected: Multiple valid formats are accepted
     */
    public void testRegex_MultipleValidCargoCodes() {
        System.out.println("TEST: testRegex_MultipleValidCargoCodes");
        String[] validCodes = {"PET-AA", "PET-AB", "PET-ZZ", "PET-MN"};
        
        for (String cargoCode : validCodes) {
            boolean result = validateCargoCode(cargoCode);
            System.out.println("Input: " + cargoCode + " -> " + (result ? "VALID" : "INVALID"));
            assert result : "Valid Cargo Code " + cargoCode + " should be accepted";
        }
        System.out.println("✓ PASSED\n");
    }

    /**
     * Run all test cases
     */
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("UC11: Regex Validation Test Suite");
        System.out.println("========================================\n");
        
        TrainConsistManagementAppTest tester = new TrainConsistManagementAppTest();
        
        try {
            tester.testRegex_ValidTrainID();
            tester.testRegex_InvalidTrainIDFormat();
            tester.testRegex_ValidCargoCode();
            tester.testRegex_InvalidCargoCodeFormat();
            tester.testRegex_TrainIDDigitLengthValidation();
            tester.testRegex_CargoCodeUppercaseValidation();
            tester.testRegex_EmptyInputHandling();
            tester.testRegex_ExactPatternMatch();
            tester.testRegex_NullInputHandling();
            tester.testRegex_MultipleValidTrainIDs();
            tester.testRegex_MultipleValidCargoCodes();
            
            System.out.println("========================================");
            System.out.println("✓ ALL 11 TESTS PASSED!");
            System.out.println("========================================");
        } catch (AssertionError e) {
            System.out.println("\n✗ TEST FAILED: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
