import java.util.ArrayList;
import java.util.List;

public class TrainConsistManagementAppTest {
    static class Bogie {
        private String name, type;
        private int capacity;
        private String cargo;

        public Bogie(String n, String t, int c) { name=n; type=t; capacity=c; cargo=null; }
        public Bogie(String n, String t, int c, String g) { name=n; type=t; capacity=c; cargo=g; }
        public String getName() { return name; }
        public String getType() { return type; }
        public int getCapacity() { return capacity; }
        public String getCargo() { return cargo; }
    }

    static class PerformanceResult {
        public List<Bogie> result;
        public long executionTimeNanos;
        public PerformanceResult(List<Bogie> r, long t) { result=r; executionTimeNanos=t; }
        public double getExecutionTimeMillis() { return executionTimeNanos/1_000_000.0; }
    }

    public static List<Bogie> filterBogiesByLoop(List<Bogie> bogies, int minCapacity) {
        List<Bogie> filtered = new ArrayList<>();
        for (Bogie bogie : bogies) {
            if (bogie.getCapacity() > minCapacity) filtered.add(bogie);
        }
        return filtered;
    }

    public static List<Bogie> filterBogiesByStream(List<Bogie> bogies, int minCapacity) {
        return bogies.stream().filter(bogie -> bogie.getCapacity() > minCapacity).collect(java.util.stream.Collectors.toList());
    }

    public static PerformanceResult benchmarkLoopFiltering(List<Bogie> bogies, int minCapacity) {
        long startTime = System.nanoTime();
        List<Bogie> result = filterBogiesByLoop(bogies, minCapacity);
        long endTime = System.nanoTime();
        return new PerformanceResult(result, endTime - startTime);
    }

    public static PerformanceResult benchmarkStreamFiltering(List<Bogie> bogies, int minCapacity) {
        long startTime = System.nanoTime();
        List<Bogie> result = filterBogiesByStream(bogies, minCapacity);
        long endTime = System.nanoTime();
        return new PerformanceResult(result, endTime - startTime);
    }

    public void testLoopFilteringLogic() {
        System.out.println("TEST: testLoopFilteringLogic");
        List<Bogie> bogies = new ArrayList<>();
        bogies.add(new Bogie("B1", "P", 50));
        bogies.add(new Bogie("B2", "P", 60));
        bogies.add(new Bogie("B3", "P", 70));
        bogies.add(new Bogie("B4", "P", 90));
        List<Bogie> filtered = filterBogiesByLoop(bogies, 60);
        assert filtered.size() == 2 && filtered.get(0).getCapacity() == 70 && filtered.get(1).getCapacity() == 90;
        System.out.println("✓ PASSED\n");
    }

    public void testStreamFilteringLogic() {
        System.out.println("TEST: testStreamFilteringLogic");
        List<Bogie> bogies = new ArrayList<>();
        bogies.add(new Bogie("B1", "P", 50));
        bogies.add(new Bogie("B2", "P", 60));
        bogies.add(new Bogie("B3", "P", 70));
        bogies.add(new Bogie("B4", "P", 90));
        List<Bogie> filtered = filterBogiesByStream(bogies, 60);
        assert filtered.size() == 2 && filtered.get(0).getCapacity() == 70 && filtered.get(1).getCapacity() == 90;
        System.out.println("✓ PASSED\n");
    }

    public void testLoopAndStreamResultsMatch() {
        System.out.println("TEST: testLoopAndStreamResultsMatch");
        List<Bogie> bogies = new ArrayList<>();
        for (int i = 0; i < 100; i++) bogies.add(new Bogie("B"+i, "G", 40+(i%80)));
        List<Bogie> loopResult = filterBogiesByLoop(bogies, 60);
        List<Bogie> streamResult = filterBogiesByStream(bogies, 60);
        assert loopResult.size() == streamResult.size();
        System.out.println("✓ PASSED\n");
    }

    public void testExecutionTimeMeasurement() {
        System.out.println("TEST: testExecutionTimeMeasurement");
        List<Bogie> bogies = new ArrayList<>();
        for (int i = 0; i < 1000; i++) bogies.add(new Bogie("B"+i, "G", 40+(i%100)));
        PerformanceResult loopResult = benchmarkLoopFiltering(bogies, 60);
        PerformanceResult streamResult = benchmarkStreamFiltering(bogies, 60);
        assert loopResult.executionTimeNanos > 0 && streamResult.executionTimeNanos > 0;
        System.out.println("✓ PASSED\n");
    }

    public void testLargeDatasetProcessing() {
        System.out.println("TEST: testLargeDatasetProcessing");
        List<Bogie> bogies = new ArrayList<>();
        for (int i = 0; i < 10000; i++) bogies.add(new Bogie("B"+i, "G", 40+(i%100)));
        List<Bogie> loopResult = filterBogiesByLoop(bogies, 60);
        List<Bogie> streamResult = filterBogiesByStream(bogies, 60);
        assert loopResult.size() > 0 && loopResult.size() == streamResult.size();
        System.out.println("✓ PASSED\n");
    }

    public void testPerformanceComparisonSmall() {
        System.out.println("TEST: testPerformanceComparisonSmall");
        List<Bogie> bogies = new ArrayList<>();
        for (int i = 0; i < 100; i++) bogies.add(new Bogie("B"+i, "G", 40+(i%80)));
        PerformanceResult loopResult = benchmarkLoopFiltering(bogies, 60);
        PerformanceResult streamResult = benchmarkStreamFiltering(bogies, 60);
        assert loopResult.result.size() == streamResult.result.size();
        System.out.println("✓ PASSED\n");
    }

    public void testPerformanceComparisonLarge() {
        System.out.println("TEST: testPerformanceComparisonLarge");
        List<Bogie> bogies = new ArrayList<>();
        for (int i = 0; i < 50000; i++) bogies.add(new Bogie("B"+i, "G", 40+(i%100)));
        PerformanceResult loopResult = benchmarkLoopFiltering(bogies, 60);
        PerformanceResult streamResult = benchmarkStreamFiltering(bogies, 60);
        assert loopResult.result.size() == streamResult.result.size();
        System.out.println("✓ PASSED\n");
    }

    public void testEmptyDatasetHandling() {
        System.out.println("TEST: testEmptyDatasetHandling");
        List<Bogie> emptyList = new ArrayList<>();
        List<Bogie> loopResult = filterBogiesByLoop(emptyList, 60);
        List<Bogie> streamResult = filterBogiesByStream(emptyList, 60);
        assert loopResult.size() == 0 && streamResult.size() == 0;
        System.out.println("✓ PASSED\n");
    }

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("UC13: Performance Comparison Test Suite");
        System.out.println("========================================\n");
        TrainConsistManagementAppTest tester = new TrainConsistManagementAppTest();
        try {
            tester.testLoopFilteringLogic();
            tester.testStreamFilteringLogic();
            tester.testLoopAndStreamResultsMatch();
            tester.testExecutionTimeMeasurement();
            tester.testLargeDatasetProcessing();
            tester.testPerformanceComparisonSmall();
            tester.testPerformanceComparisonLarge();
            tester.testEmptyDatasetHandling();
            System.out.println("========================================");
            System.out.println("✓ ALL 8 TESTS PASSED!");
            System.out.println("========================================");
        } catch (AssertionError e) {
            System.out.println("\n✗ TEST FAILED: " + e.getMessage());
            e.printStackTrace();
        }
    }
}