import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Test Cases for UC8: Filter Passenger Bogies Using Streams
 * 
 * This test class verifies that the Stream API filtering operations work correctly
 * for various scenarios including boundary conditions and edge cases.
 */
public class TrainConsistManagementAppTest {
    
    private List<Bogie> testBogies;

    // Setup method - initializes test data
    public void setUp() {
        testBogies = new ArrayList<>();
        testBogies.add(new Bogie("Sleeper", 72));
        testBogies.add(new Bogie("AC Chair", 96));
        testBogies.add(new Bogie("First Class", 48));
        testBogies.add(new Bogie("General", 120));
        testBogies.add(new Bogie("AC Sleeper", 60));
    }

    /**
     * Test: Filter bogies with capacity greater than threshold (70)
     * Expected: Only bogies with capacity > 70 should be returned
     */
    public void testFilter_CapacityGreaterThanThreshold() {
        setUp();
        int threshold = 70;
        
        List<Bogie> filtered = testBogies.stream()
                .filter(bogie -> bogie.getCapacity() > threshold)
                .collect(Collectors.toList());
        
        System.out.println("TEST: testFilter_CapacityGreaterThanThreshold");
        System.out.println("Expected: 3 bogies (Sleeper-72, AC Chair-96, General-120)");
        System.out.println("Actual: " + filtered.size() + " bogies");
        assert filtered.size() == 3 : "Expected 3 bogies, got " + filtered.size();
        
        // Verify each bogie has capacity > 70
        for (Bogie bogie : filtered) {
            assert bogie.getCapacity() > threshold : "Bogie " + bogie.getName() + " has capacity " + bogie.getCapacity() + " which is not > " + threshold;
        }
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Filter bogies with capacity equal to threshold (70)
     * Expected: No bogies should be returned (none have exactly 70 seats)
     */
    public void testFilter_CapacityEqualToThreshold() {
        setUp();
        int threshold = 70;
        
        List<Bogie> filtered = testBogies.stream()
                .filter(bogie -> bogie.getCapacity() == threshold)
                .collect(Collectors.toList());
        
        System.out.println("TEST: testFilter_CapacityEqualToThreshold");
        System.out.println("Expected: 0 bogies (no bogie with exactly 70 seats)");
        System.out.println("Actual: " + filtered.size() + " bogies");
        assert filtered.size() == 0 : "Expected 0 bogies, got " + filtered.size();
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Filter bogies with capacity less than threshold (70)
     * Expected: Only bogies with capacity < 70 should be returned
     */
    public void testFilter_CapacityLessThanThreshold() {
        setUp();
        int threshold = 70;
        
        List<Bogie> filtered = testBogies.stream()
                .filter(bogie -> bogie.getCapacity() < threshold)
                .collect(Collectors.toList());
        
        System.out.println("TEST: testFilter_CapacityLessThanThreshold");
        System.out.println("Expected: 2 bogies (First Class-48, AC Sleeper-60)");
        System.out.println("Actual: " + filtered.size() + " bogies");
        assert filtered.size() == 2 : "Expected 2 bogies, got " + filtered.size();
        
        // Verify each bogie has capacity < 70
        for (Bogie bogie : filtered) {
            assert bogie.getCapacity() < threshold : "Bogie " + bogie.getName() + " has capacity " + bogie.getCapacity() + " which is not < " + threshold;
        }
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Multiple matching bogies with combined filter condition
     * Expected: All bogies matching capacity >= 60 AND <= 100
     */
    public void testFilter_MultipleBogiesMatching() {
        setUp();
        
        List<Bogie> filtered = testBogies.stream()
                .filter(bogie -> bogie.getCapacity() >= 60 && bogie.getCapacity() <= 100)
                .collect(Collectors.toList());
        
        System.out.println("TEST: testFilter_MultipleBogiesMatching");
        System.out.println("Expected: 3 bogies (Sleeper-72, AC Chair-96, AC Sleeper-60)");
        System.out.println("Actual: " + filtered.size() + " bogies");
        assert filtered.size() == 3 : "Expected 3 bogies, got " + filtered.size();
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: No bogies matching filter condition
     * Expected: Empty list should be returned
     */
    public void testFilter_NoBogiesMatching() {
        setUp();
        
        List<Bogie> filtered = testBogies.stream()
                .filter(bogie -> bogie.getCapacity() > 1000)  // Extremely high threshold
                .collect(Collectors.toList());
        
        System.out.println("TEST: testFilter_NoBogiesMatching");
        System.out.println("Expected: 0 bogies (no bogie with capacity > 1000)");
        System.out.println("Actual: " + filtered.size() + " bogies");
        assert filtered.isEmpty() : "Expected empty list, but got " + filtered.size() + " bogies";
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: All bogies matching filter condition
     * Expected: All original bogies should be returned
     */
    public void testFilter_AllBogiesMatching() {
        setUp();
        
        List<Bogie> filtered = testBogies.stream()
                .filter(bogie -> bogie.getCapacity() > 0)  // Very low threshold
                .collect(Collectors.toList());
        
        System.out.println("TEST: testFilter_AllBogiesMatching");
        System.out.println("Expected: 5 bogies (all original bogies)");
        System.out.println("Actual: " + filtered.size() + " bogies");
        assert filtered.size() == 5 : "Expected 5 bogies, got " + filtered.size();
        assert filtered.size() == testBogies.size() : "Filtered list size should equal original list size";
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Filtering an empty bogie list
     * Expected: Empty list should be returned without errors
     */
    public void testFilter_EmptyBogieList() {
        testBogies = new ArrayList<>();  // Empty list
        
        List<Bogie> filtered = testBogies.stream()
                .filter(bogie -> bogie.getCapacity() > 70)
                .collect(Collectors.toList());
        
        System.out.println("TEST: testFilter_EmptyBogieList");
        System.out.println("Expected: 0 bogies (filtering empty list)");
        System.out.println("Actual: " + filtered.size() + " bogies");
        assert filtered.isEmpty() : "Expected empty list for empty input";
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Original list remains unchanged after filtering
     * Expected: Original collection should have same size and contents
     */
    public void testFilter_OriginalListUnchanged() {
        setUp();
        int originalSize = testBogies.size();
        List<String> originalNames = new ArrayList<>();
        for (Bogie bogie : testBogies) {
            originalNames.add(bogie.getName());
        }
        
        // Perform filtering operation
        List<Bogie> filtered = testBogies.stream()
                .filter(bogie -> bogie.getCapacity() > 70)
                .collect(Collectors.toList());
        
        System.out.println("TEST: testFilter_OriginalListUnchanged");
        System.out.println("Original list size before: " + originalSize);
        System.out.println("Original list size after: " + testBogies.size());
        System.out.println("Filtered list size: " + filtered.size());
        
        assert testBogies.size() == originalSize : "Original list size changed from " + originalSize + " to " + testBogies.size();
        
        List<String> currentNames = new ArrayList<>();
        for (Bogie bogie : testBogies) {
            currentNames.add(bogie.getName());
        }
        assert currentNames.equals(originalNames) : "Original list contents were modified";
        System.out.println("✓ PASSED: Original list integrity maintained\n");
    }

    /**
     * Run all test cases
     */
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("UC8: Stream Filtering Test Suite");
        System.out.println("========================================\n");
        
        TrainConsistManagementAppTest tester = new TrainConsistManagementAppTest();
        
        try {
            tester.testFilter_CapacityGreaterThanThreshold();
            tester.testFilter_CapacityEqualToThreshold();
            tester.testFilter_CapacityLessThanThreshold();
            tester.testFilter_MultipleBogiesMatching();
            tester.testFilter_NoBogiesMatching();
            tester.testFilter_AllBogiesMatching();
            tester.testFilter_EmptyBogieList();
            tester.testFilter_OriginalListUnchanged();
            
            System.out.println("========================================");
            System.out.println("✓ ALL TESTS PASSED!");
            System.out.println("========================================");
        } catch (AssertionError e) {
            System.out.println("\n✗ TEST FAILED: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
