import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Test Cases for UC10: Count Total Seats Using reduce()
 * 
 * This test class verifies that the Stream API reduction operations work correctly
 * for aggregating bogie capacities and computing meaningful metrics.
 */
public class TrainConsistManagementAppTest {
    
    private List<Bogie> testBogies;

    // Setup method - initializes test data
    public void setUp() {
        testBogies = new ArrayList<>();
        testBogies.add(new Bogie("Sleeper", "Passenger", 72));
        testBogies.add(new Bogie("AC Chair", "Passenger", 96));
        testBogies.add(new Bogie("First Class", "Passenger", 48));
        testBogies.add(new Bogie("General", "Passenger", 120));
        testBogies.add(new Bogie("AC Sleeper", "Passenger", 60));
        testBogies.add(new Bogie("Rectangular", "Goods", 500));
        testBogies.add(new Bogie("Cylindrical", "Goods", 400));
    }

    /**
     * Test: Total seat calculation for all bogies
     * Expected: Sum of all capacities (72+96+48+120+60+500+400 = 1296)
     */
    public void testReduce_TotalSeatCalculation() {
        setUp();
        
        int totalCapacity = testBogies.stream()
                .map(Bogie::getCapacity)
                .reduce(0, Integer::sum);
        
        System.out.println("TEST: testReduce_TotalSeatCalculation");
        System.out.println("Expected: 1296 seats (72+96+48+120+60+500+400)");
        System.out.println("Actual: " + totalCapacity + " seats");
        
        assert totalCapacity == 1296 : "Expected 1296, got " + totalCapacity;
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Aggregation of multiple bogies
     * Expected: All bogie capacities correctly included in total
     */
    public void testReduce_MultipleBogiesAggregation() {
        setUp();
        
        int totalCapacity = testBogies.stream()
                .map(Bogie::getCapacity)
                .reduce(0, Integer::sum);
        
        System.out.println("TEST: testReduce_MultipleBogiesAggregation");
        System.out.println("Expected: " + (72+96+48+120+60+500+400) + " seats");
        System.out.println("Actual: " + totalCapacity + " seats");
        System.out.println("Number of bogies: " + testBogies.size());
        
        assert totalCapacity > 0 : "Total capacity should be positive";
        assert testBogies.size() == 7 : "Should have 7 bogies";
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Single bogie handling
     * Expected: Total equals the single bogie's capacity
     */
    public void testReduce_SingleBogieCapacity() {
        testBogies = new ArrayList<>();
        testBogies.add(new Bogie("Sleeper", "Passenger", 72));
        
        int totalCapacity = testBogies.stream()
                .map(Bogie::getCapacity)
                .reduce(0, Integer::sum);
        
        System.out.println("TEST: testReduce_SingleBogieCapacity");
        System.out.println("Expected: 72 seats (single bogie)");
        System.out.println("Actual: " + totalCapacity + " seats");
        
        assert totalCapacity == 72 : "Expected 72, got " + totalCapacity;
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Empty bogie list handling
     * Expected: Result should be 0 (identity value)
     */
    public void testReduce_EmptyBogieList() {
        testBogies = new ArrayList<>();  // Empty list
        
        int totalCapacity = testBogies.stream()
                .map(Bogie::getCapacity)
                .reduce(0, Integer::sum);
        
        System.out.println("TEST: testReduce_EmptyBogieList");
        System.out.println("Expected: 0 seats (empty list)");
        System.out.println("Actual: " + totalCapacity + " seats");
        
        assert totalCapacity == 0 : "Expected 0 for empty list, got " + totalCapacity;
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Correct capacity extraction using map()
     * Expected: All capacities correctly extracted from Bogie objects
     */
    public void testReduce_CorrectCapacityExtraction() {
        setUp();
        
        int totalCapacity = testBogies.stream()
                .map(Bogie::getCapacity)
                .reduce(0, Integer::sum);
        
        // Calculate expected manually
        int expectedTotal = 0;
        for (Bogie bogie : testBogies) {
            expectedTotal += bogie.getCapacity();
        }
        
        System.out.println("TEST: testReduce_CorrectCapacityExtraction");
        System.out.println("Expected: " + expectedTotal + " seats");
        System.out.println("Actual: " + totalCapacity + " seats");
        
        assert totalCapacity == expectedTotal : "Capacity extraction mismatch";
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: All bogies included in aggregation
     * Expected: Total reflects all bogies in collection
     */
    public void testReduce_AllBogiesIncluded() {
        setUp();
        int bogieCount = testBogies.size();
        
        int totalCapacity = testBogies.stream()
                .map(Bogie::getCapacity)
                .reduce(0, Integer::sum);
        
        System.out.println("TEST: testReduce_AllBogiesIncluded");
        System.out.println("Bogie count: " + bogieCount);
        System.out.println("Total capacity: " + totalCapacity + " seats");
        
        // Verify each bogie contributes
        int verificationTotal = 0;
        for (Bogie bogie : testBogies) {
            verificationTotal += bogie.getCapacity();
        }
        
        assert totalCapacity == verificationTotal : "Not all bogies included";
        System.out.println("✓ PASSED: All " + bogieCount + " bogies included\n");
    }

    /**
     * Test: Original list unchanged after reduction
     * Expected: Original collection size and contents remain same
     */
    public void testReduce_OriginalListUnchanged() {
        setUp();
        int originalSize = testBogies.size();
        List<String> originalNames = new ArrayList<>();
        for (Bogie bogie : testBogies) {
            originalNames.add(bogie.getName());
        }
        
        // Perform reduction
        int totalCapacity = testBogies.stream()
                .map(Bogie::getCapacity)
                .reduce(0, Integer::sum);
        
        System.out.println("TEST: testReduce_OriginalListUnchanged");
        System.out.println("Original list size before: " + originalSize);
        System.out.println("Original list size after: " + testBogies.size());
        
        assert testBogies.size() == originalSize : "Original list size changed";
        
        List<String> currentNames = new ArrayList<>();
        for (Bogie bogie : testBogies) {
            currentNames.add(bogie.getName());
        }
        assert currentNames.equals(originalNames) : "Original list contents were modified";
        System.out.println("✓ PASSED: Original list integrity maintained\n");
    }

    /**
     * Test: Passenger bogie aggregation
     * Expected: Sum of only passenger bogie capacities
     */
    public void testReduce_PassengerBogiesOnly() {
        setUp();
        
        int passengerTotal = testBogies.stream()
                .filter(bogie -> "Passenger".equals(bogie.getType()))
                .map(Bogie::getCapacity)
                .reduce(0, Integer::sum);
        
        System.out.println("TEST: testReduce_PassengerBogiesOnly");
        System.out.println("Expected: 396 seats (72+96+48+120+60)");
        System.out.println("Actual: " + passengerTotal + " seats");
        
        assert passengerTotal == 396 : "Expected 396, got " + passengerTotal;
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Goods bogie aggregation
     * Expected: Sum of only goods bogie capacities
     */
    public void testReduce_GoodsBogiesOnly() {
        setUp();
        
        int goodsTotal = testBogies.stream()
                .filter(bogie -> "Goods".equals(bogie.getType()))
                .map(Bogie::getCapacity)
                .reduce(0, Integer::sum);
        
        System.out.println("TEST: testReduce_GoodsBogiesOnly");
        System.out.println("Expected: 900 units (500+400)");
        System.out.println("Actual: " + goodsTotal + " units");
        
        assert goodsTotal == 900 : "Expected 900, got " + goodsTotal;
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Maximum capacity using reduce
     * Expected: Largest bogie capacity value
     */
    public void testReduce_MaximumCapacity() {
        setUp();
        
        int maxCapacity = testBogies.stream()
                .map(Bogie::getCapacity)
                .reduce(0, Integer::max);
        
        System.out.println("TEST: testReduce_MaximumCapacity");
        System.out.println("Expected: 500 (Rectangular bogie)");
        System.out.println("Actual: " + maxCapacity + " seats");
        
        assert maxCapacity == 500 : "Expected 500, got " + maxCapacity;
        System.out.println("✓ PASSED\n");
    }

    /**
     * Run all test cases
     */
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("UC10: Stream Reduction Test Suite");
        System.out.println("========================================\n");
        
        TrainConsistManagementAppTest tester = new TrainConsistManagementAppTest();
        
        try {
            tester.testReduce_TotalSeatCalculation();
            tester.testReduce_MultipleBogiesAggregation();
            tester.testReduce_SingleBogieCapacity();
            tester.testReduce_EmptyBogieList();
            tester.testReduce_CorrectCapacityExtraction();
            tester.testReduce_AllBogiesIncluded();
            tester.testReduce_OriginalListUnchanged();
            tester.testReduce_PassengerBogiesOnly();
            tester.testReduce_GoodsBogiesOnly();
            tester.testReduce_MaximumCapacity();
            
            System.out.println("========================================");
            System.out.println("✓ ALL 10 TESTS PASSED!");
            System.out.println("========================================");
        } catch (AssertionError e) {
            System.out.println("\n✗ TEST FAILED: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
