import java.util.HashSet;
import java.util.Set;

public class TrainConsistManagementApp {
    public static void main(String[] args) {
        System.out.println("=== Train Consist Management App ===");

        Set<String> bogieIds = new HashSet<>();

        bogieIds.add("BG101");
        bogieIds.add("BG102");
        bogieIds.add("BG101"); // duplicate intentionally added
        bogieIds.add("BG103");
        bogieIds.add("BG102"); // duplicate intentionally added

        System.out.println("Unique bogie IDs after insertion: " + bogieIds);
        System.out.println("Total unique bogie count: " + bogieIds.size());
    }
}
