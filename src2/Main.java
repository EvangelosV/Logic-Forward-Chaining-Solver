import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Load the knowledge from txt file
        System.out.println("_________________________________________________________");
        System.out.print("Enter the knowledge base file path: ");
        String filePath = scanner.nextLine();

        List<Rule> rules = new ArrayList<>();
        Set<String> facts = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.contains("->")) {
                    String[] parts = line.split("->");
                    String[] premises = parts[0].trim().split("\\s+");
                    String conclusion = parts[1].trim();
                    rules.add(new Rule(Arrays.asList(premises), conclusion));
                } else {
                    facts.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            scanner.close();
            return;
        }

        // Print the knowledge base
        System.out.println("\nKnowledge Base:");
        System.out.println("Rules:");
        for (Rule rule : rules) {
            System.out.println(rule.toString());
        }
        System.out.println("\nFacts:");
        for (String fact : facts) {
            System.out.println(fact);
        }

        // Query input
        System.out.println("\nEnter the query to prove: ");
        String query = scanner.nextLine().trim();

        PL_FC_Entails fc = new PL_FC_Entails();
        boolean result = fc.forwardChaining(rules, facts, query);

        System.out.println("\nResult:");
        if (result) {
            System.out.println(query + " is true.");
        } else {
            System.out.println(query + " is false.");
        }
        System.out.println("_________________________________________________________");

        scanner.close();
    }
}
