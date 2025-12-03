import java.util.*;

public class PL_FC_Entails {

    public boolean forwardChaining(List<Rule> rules, Set<String> facts, String query) {
        // Initializing
        Map<Rule, Integer> count = new HashMap<>(); // Number of unsatisfied premises for each rule
        Map<String, Boolean> inferred = new HashMap<>();  // If a symbol has been inferred
        Queue<String> agenda = new LinkedList<>(facts);   // Symbols known to be true initially

        // Set up count for each clause
        for (Rule rule : rules) {
            count.put(rule, rule.premises.size());
            inferred.putIfAbsent(rule.conclusion, false);
            for (String premise : rule.premises) {
                inferred.putIfAbsent(premise, false);
            }
        }

        // Forward Chaining
        while (!agenda.isEmpty()) {
            String p = agenda.poll();

            // if not already inferred, proccess
            if (!inferred.get(p)) {
                inferred.put(p, true);

                // Iterate over rules whose premise includes p
                for (Rule rule : rules) {
                    if (rule.premises.contains(p)) {
                        count.put(rule, count.get(rule) - 1); 

                        if (count.get(rule) == 0) { // all premises satisfied
                            if (rule.conclusion.equals(query)) {
                                return true; // proved
                            }
                            agenda.add(rule.conclusion);
                        }
                    }
                }
            }
        }

        return false; // Query not proved
    }
}
