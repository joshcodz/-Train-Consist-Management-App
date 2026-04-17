import java.util.ArrayList;
import java.util.List;

public class TrainConsistManagementApp {
    public static void main(String[] args) {
        System.out.println("=== Train Consist Management App ===");

        List<String> passengerBogies = new ArrayList<>();

        passengerBogies.add("Sleeper");
        passengerBogies.add("AC Chair");
        passengerBogies.add("First Class");

        System.out.println("Passenger bogies after insertion: " + passengerBogies);

        passengerBogies.remove("AC Chair");
        System.out.println("Passenger bogies after removing AC Chair: " + passengerBogies);

        boolean sleeperExists = passengerBogies.contains("Sleeper");
        System.out.println("Does Sleeper bogie exist? " + sleeperExists);

        System.out.println("Final passenger bogie list: " + passengerBogies);
    }
}
