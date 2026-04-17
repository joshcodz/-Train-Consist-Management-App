import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Test Cases for UC9: Group Bogies by Type (Collectors.groupingBy)
 * 
 * This test class verifies that the Stream API grouping operations work correctly
 * for various scenarios including boundary conditions and different grouping strategies.
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
     * Test: Bogies grouped by Type
     * Expected: Two groups (Passenger and Goods) with correct counts
     */
    public void testGrouping_BogiesGroupedByType() {
        setUp();
        
        Map<String, List<Bogie>> grouped = testBogies.stream()
                .collect(Collectors.groupingBy(Bogie::getType));
        
        System.out.println("TEST: testGrouping_BogiesGroupedByType");
        System.out.println("Expected: 2 groups (Passenger: 5, Goods: 2)");
        System.out.println("Actual: " + grouped.size() + " groups");
        
        assert grouped.size() == 2 : "Expected 2 groups, got " + grouped.size();
        assert grouped.containsKey("Passenger") : "Missing 'Passenger' group";
        assert grouped.containsKey("Goods") : "Missing 'Goods' group";
        assert grouped.get("Passenger").size() == 5 : "Expected 5 passenger bogies";
        assert grouped.get("Goods").size() == 2 : "Expected 2 goods bogies";
        
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Multiple bogies in same group
     * Expected: Multiple bogies should be in the same group list
     */
    public void testGrouping_MultipleBogiesInSameGroup() {
        setUp();
        
        Map<String, List<Bogie>> grouped = testBogies.stream()
                .collect(Collectors.groupingBy(Bogie::getType));
        
        System.out.println("TEST: testGrouping_MultipleBogiesInSameGroup");
        System.out.println("Expected: Passenger group has multiple bogies");
        
        List<Bogie> passengerBogies = grouped.get("Passenger");
        assert passengerBogies.size() > 1 : "Expected multiple bogies in Passenger group";
        
        for (Bogie bogie : passengerBogies) {
            assert "Passenger".equals(bogie.getType()) : "Bogie in wrong group";
        }
        
        System.out.println("Actual: Passenger group has " + passengerBogies.size() + " bogies");
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Different bogie types in different groups
     * Expected: Each group should contain only one type
     */
    public void testGrouping_DifferentBogieTypes() {
        setUp();
        
        Map<String, List<Bogie>> grouped = testBogies.stream()
                .collect(Collectors.groupingBy(Bogie::getType));
        
        System.out.println("TEST: testGrouping_DifferentBogieTypes");
        
        for (String type : grouped.keySet()) {
            List<Bogie> boggiesOfType = grouped.get(type);
            for (Bogie bogie : boggiesOfType) {
                assert type.equals(bogie.getType()) : "Bogie " + bogie.getName() + " in wrong type group";
            }
            System.out.println("Type: " + type + " contains " + boggiesOfType.size() + " bogies");
        }
        
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Empty bogie list grouping
     * Expected: Empty map should be returned
     */
    public void testGrouping_EmptyBogieList() {
        testBogies = new ArrayList<>();  // Empty list
        
        Map<String, List<Bogie>> grouped = testBogies.stream()
                .collect(Collectors.groupingBy(Bogie::getType));
        
        System.out.println("TEST: testGrouping_EmptyBogieList");
        System.out.println("Expected: 0 groups (empty map)");
        System.out.println("Actual: " + grouped.size() + " groups");
        
        assert grouped.isEmpty() : "Expected empty map, got " + grouped.size() + " groups";
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Single bogie category
     * Expected: Map with one key containing all bogies
     */
    public void testGrouping_SingleBogieCategory() {
        testBogies = new ArrayList<>();
        testBogies.add(new Bogie("Sleeper", "Passenger", 72));
        testBogies.add(new Bogie("AC Chair", "Passenger", 96));
        
        Map<String, List<Bogie>> grouped = testBogies.stream()
                .collect(Collectors.groupingBy(Bogie::getType));
        
        System.out.println("TEST: testGrouping_SingleBogieCategory");
        System.out.println("Expected: 1 group (all Passenger)");
        System.out.println("Actual: " + grouped.size() + " groups");
        
        assert grouped.size() == 1 : "Expected 1 group, got " + grouped.size();
        assert grouped.containsKey("Passenger") : "Expected 'Passenger' group";
        assert grouped.get("Passenger").size() == 2 : "Expected 2 bogies in group";
        
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Map contains correct keys
     * Expected: All group keys should match the expected types
     */
    public void testGrouping_MapContainsCorrectKeys() {
        setUp();
        
        Map<String, List<Bogie>> grouped = testBogies.stream()
                .collect(Collectors.groupingBy(Bogie::getType));
        
        System.out.println("TEST: testGrouping_MapContainsCorrectKeys");
        System.out.println("Expected keys: 'Passenger', 'Goods'");
        System.out.println("Actual keys: " + grouped.keySet());
        
        assert grouped.keySet().contains("Passenger") : "Missing 'Passenger' key";
        assert grouped.keySet().contains("Goods") : "Missing 'Goods' key";
        
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Group size validation
     * Expected: Each group should have the correct number of bogies
     */
    public void testGrouping_GroupSizeValidation() {
        setUp();
        
        Map<String, List<Bogie>> grouped = testBogies.stream()
                .collect(Collectors.groupingBy(Bogie::getType));
        
        System.out.println("TEST: testGrouping_GroupSizeValidation");
        System.out.println("Expected: Passenger=5 bogies, Goods=2 bogies");
        
        int passengerCount = grouped.get("Passenger").size();
        int goodsCount = grouped.get("Goods").size();
        
        System.out.println("Actual: Passenger=" + passengerCount + " bogies, Goods=" + goodsCount + " bogies");
        
        assert passengerCount == 5 : "Expected 5 passenger bogies, got " + passengerCount;
        assert goodsCount == 2 : "Expected 2 goods bogies, got " + goodsCount;
        
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Original list remains unchanged after grouping
     * Expected: Original collection should have same size and contents
     */
    public void testGrouping_OriginalListUnchanged() {
        setUp();
        int originalSize = testBogies.size();
        List<String> originalNames = new ArrayList<>();
        for (Bogie bogie : testBogies) {
            originalNames.add(bogie.getName());
        }
        
        // Perform grouping operation
        Map<String, List<Bogie>> grouped = testBogies.stream()
                .collect(Collectors.groupingBy(Bogie::getType));
        
        System.out.println("TEST: testGrouping_OriginalListUnchanged");
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
     * Test: Grouping by name field
     * Expected: Each bogie name should be a different key
     */
    public void testGrouping_GroupingByName() {
        setUp();
        
        Map<String, List<Bogie>> groupedByName = testBogies.stream()
                .collect(Collectors.groupingBy(Bogie::getName));
        
        System.out.println("TEST: testGrouping_GroupingByName");
        System.out.println("Expected: 7 groups (one per unique bogie name)");
        System.out.println("Actual: " + groupedByName.size() + " groups");
        
        assert groupedByName.size() == 7 : "Expected 7 groups, got " + groupedByName.size();
        
        for (String name : groupedByName.keySet()) {
            assert groupedByName.get(name).size() == 1 : "Each name group should have 1 bogie";
            System.out.println("  Group '" + name + "': " + groupedByName.get(name).size() + " bogie");
        }
        
        System.out.println("✓ PASSED\n");
    }

    /**
     * Test: Grouping by capacity range
     * Expected: Bogies should be grouped into capacity ranges
     */
    public void testGrouping_GroupingByCapacityRange() {
        setUp();
        
        Map<String, List<Bogie>> groupedByRange = testBogies.stream()
                .collect(Collectors.groupingBy(bogie -> {
                    if (bogie.getCapacity() < 100) {
                        return "Low";
                    } else if (bogie.getCapacity() <= 200) {
                        return "Medium";
                    } else {
                        return "High";
                    }
                }));
        
        System.out.println("TEST: testGrouping_GroupingByCapacityRange");
        System.out.println("Expected: 3 ranges (Low, Medium, High)");
        System.out.println("Actual: " + groupedByRange.size() + " ranges");
        
        assert groupedByRange.size() == 3 : "Expected 3 capacity ranges";
        assert groupedByRange.get("Low").size() == 4 : "Expected 4 low-capacity bogies";
        assert groupedByRange.get("Medium").size() == 1 : "Expected 1 medium-capacity bogie";
        assert groupedByRange.get("High").size() == 2 : "Expected 2 high-capacity bogies";
        
        System.out.println("✓ PASSED\n");
    }

    /**
     * Run all test cases
     */
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("UC9: Stream Grouping Test Suite");
        System.out.println("========================================\n");
        
        TrainConsistManagementAppTest tester = new TrainConsistManagementAppTest();
        
        try {
            tester.testGrouping_BogiesGroupedByType();
            tester.testGrouping_MultipleBogiesInSameGroup();
            tester.testGrouping_DifferentBogieTypes();
            tester.testGrouping_EmptyBogieList();
            tester.testGrouping_SingleBogieCategory();
            tester.testGrouping_MapContainsCorrectKeys();
            tester.testGrouping_GroupSizeValidation();
            tester.testGrouping_OriginalListUnchanged();
            tester.testGrouping_GroupingByName();
            tester.testGrouping_GroupingByCapacityRange();
            
            System.out.println("========================================");
            System.out.println("✓ ALL 10 TESTS PASSED!");
            System.out.println("========================================");
        } catch (AssertionError e) {
            System.out.println("\n✗ TEST FAILED: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
