import java.util.*;
import java.io.*;


public class FOL_FC_main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> facts = new ArrayList<>();
        List<Rule> rules = new ArrayList<>();

        System.out.print("Enter the knowledge base file path: ");
        String filename = scanner.nextLine();


        // ΦΟΡΤΩΣΕ ΜΠΡΕ ΑΠΟ ΤΟ ΑΡΧΕΙΟ ΤΣΙ ΚΑΝΟΝΕΣ
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                if (line.contains("->")) {
                    String[] parts = line.split("->");
                    String[] premises = parts[0].split("∧");
                    List<String> premisesList = new ArrayList<>();
                    for (String premise : premises) {
                        premisesList.add(premise.trim());
                    }
                    rules.add(new Rule(premisesList, parts[1].trim()));
                } else {
                    facts.add(line);
            }
        }
        } catch (IOException e) { // ΣΑΝΝΑ ΑΠΟΓΙΝΕ ΓΡΟΘΙΑ
            e.printStackTrace();
        }
        System.out.println("Knowledge Base has been loaded.\nFacts: " + facts + "\nRules: " + rules);

        FC forwardChaining = new FC(facts, rules); // ΤΡΕΧΑ ΜΠΡΕ ΤΟΝ ΑΛΓΟΡΙΘΜΟ ΜΠΡΟΣΤΙΝΗΣ ΑΛΥΣΙΔΑΣ

        //String goal = "Knows(John,Alice)";

        System.out.println("Enter sentence to prove: ");
        String goal = scanner.nextLine();
        scanner.close();

        boolean result = forwardChaining.infer(goal);
        if (result)System.out.println("Sentence : " + goal + " can be proved!");
        else System.out.println("Sentence : " + goal + " cannot be proved :(");
    }
}

