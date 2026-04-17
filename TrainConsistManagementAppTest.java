import java.util.ArrayList;
import java.util.List;

public class TrainConsistManagementAppTest {
    int passedTests = 0;
    int failedTests = 0;

    public void testException_ValidCapacityCreation() {
        System.out.println("TEST: testException_ValidCapacityCreation");
        try {
            Bogie validBogie = new Bogie("Sleeper", "Passenger", 72);
            System.out.println("  Created successfully: " + validBogie);
            System.out.println("  Type: " + validBogie.getType());
            System.out.println("  Capacity: " + validBogie.getCapacity());
            assert validBogie.getCapacity() == 72 : "Capacity should be 72";
            assert "Passenger".equals(validBogie.getType()) : "Type should be Passenger";
            System.out.println("✓ PASSED\n");
            passedTests++;
        } catch (InvalidCapacityException e) {
            System.out.println("✗ FAILED: Unexpected exception: " + e.getMessage() + "\n");
            failedTests++;
        }
    }

    public void testException_NegativeCapacityThrowsException() {
        System.out.println("TEST: testException_NegativeCapacityThrowsException");
        try {
            Bogie negativeBogie = new Bogie("TestBogie", "Passenger", -10);
            System.out.println("✗ FAILED: Should have thrown InvalidCapacityException\n");
            failedTests++;
        } catch (InvalidCapacityException e) {
            System.out.println("  Caught exception: " + e.getMessage());
            assert e.getMessage().equals("Capacity must be greater than zero") : "Message should match";
            System.out.println("✓ PASSED\n");
            passedTests++;
        }
    }

    public void testException_ZeroCapacityThrowsException() {
        System.out.println("TEST: testException_ZeroCapacityThrowsException");
        try {
            Bogie zeroBogie = new Bogie("TestBogie", "Passenger", 0);
            System.out.println("✗ FAILED: Should have thrown InvalidCapacityException\n");
            failedTests++;
        } catch (InvalidCapacityException e) {
            System.out.println("  Caught exception: " + e.getMessage());
            assert e.getMessage().equals("Capacity must be greater than zero") : "Message should match";
            System.out.println("✓ PASSED\n");
            passedTests++;
        }
    }

    public void testException_ExceptionMessageValidation() {
        System.out.println("TEST: testException_ExceptionMessageValidation");
        String expectedMessage = "Capacity must be greater than zero";
        try {
            Bogie invalidBogie = new Bogie("TestBogie", "Goods", -50);
            System.out.println("✗ FAILED: Should have thrown InvalidCapacityException\n");
            failedTests++;
        } catch (InvalidCapacityException e) {
            System.out.println("  Expected message: " + expectedMessage);
            System.out.println("  Actual message:   " + e.getMessage());
            assert expectedMessage.equals(e.getMessage()) : "Exception message should be exact";
            System.out.println("✓ PASSED\n");
            passedTests++;
        }
    }

    public void testException_ObjectIntegrityAfterCreation() {
        System.out.println("TEST: testException_ObjectIntegrityAfterCreation");
        try {
            Bogie bogie = new Bogie("AC Chair", "Passenger", 96, null);
            System.out.println("  Created: " + bogie);
            System.out.println("  Validating properties:");
            System.out.println("    Name: " + bogie.getName());
            assert "AC Chair".equals(bogie.getName()) : "Name should match";
            System.out.println("    Type: " + bogie.getType());
            assert "Passenger".equals(bogie.getType()) : "Type should match";
            System.out.println("    Capacity: " + bogie.getCapacity());
            assert 96 == bogie.getCapacity() : "Capacity should match";
            System.out.println("    Cargo: " + bogie.getCargo());
            assert bogie.getCargo() == null : "Cargo should be null";
            System.out.println("✓ PASSED\n");
            passedTests++;
        } catch (InvalidCapacityException e) {
            System.out.println("✗ FAILED: Unexpected exception: " + e.getMessage() + "\n");
            failedTests++;
        }
    }

    public void testException_MultipleValidBogiesCreation() {
        System.out.println("TEST: testException_MultipleValidBogiesCreation");
        try {
            Bogie bogie1 = new Bogie("Bogie-A", "Passenger", 50);
            Bogie bogie2 = new Bogie("Bogie-B", "Goods", 150, "Coal");
            Bogie bogie3 = new Bogie("Bogie-C", "Passenger", 75);
            Bogie bogie4 = new Bogie("Bogie-D", "Goods", 200, "Petroleum");
            Bogie bogie5 = new Bogie("Bogie-E", "Passenger", 100);
            
            System.out.println("  Created 5 bogies successfully:");
            System.out.println("    " + bogie1);
            System.out.println("    " + bogie2);
            System.out.println("    " + bogie3);
            System.out.println("    " + bogie4);
            System.out.println("    " + bogie5);
            
            assert bogie1.getCapacity() == 50 : "Bogie1 capacity should be 50";
            assert bogie2.getCapacity() == 150 : "Bogie2 capacity should be 150";
            assert bogie3.getCapacity() == 75 : "Bogie3 capacity should be 75";
            assert bogie4.getCapacity() == 200 : "Bogie4 capacity should be 200";
            assert bogie5.getCapacity() == 100 : "Bogie5 capacity should be 100";
            
            System.out.println("✓ PASSED\n");
            passedTests++;
        } catch (InvalidCapacityException e) {
            System.out.println("✗ FAILED: Unexpected exception: " + e.getMessage() + "\n");
            failedTests++;
        }
    }

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("UC14: Custom Exception Handling Test Suite");
        System.out.println("========================================\n");
        
        TrainConsistManagementAppTest tester = new TrainConsistManagementAppTest();
        
        tester.testException_ValidCapacityCreation();
        tester.testException_NegativeCapacityThrowsException();
        tester.testException_ZeroCapacityThrowsException();
        tester.testException_ExceptionMessageValidation();
        tester.testException_ObjectIntegrityAfterCreation();
        tester.testException_MultipleValidBogiesCreation();
        
        System.out.println("========================================");
        System.out.println("Tests Passed: " + tester.passedTests);
        System.out.println("Tests Failed: " + tester.failedTests);
        System.out.println("========================================");
        
        if (tester.failedTests == 0) {
            System.out.println("✓ ALL " + tester.passedTests + " TESTS PASSED!");
        } else {
            System.out.println("✗ SOME TESTS FAILED!");
        }
        System.out.println("========================================");
    }
}