import java.util.*;

public class FC {
    List<String> facts;  // Λίστα γεγονότων
    List<Rule> rules;    // Λίστα κανόνων

    public FC(List<String> facts, List<Rule> rules) {
        this.facts = new ArrayList<>(facts);
        this.rules = rules;
    }

    public boolean infer(String goal) {
        Set<String> inferred = new HashSet<>(facts);
        goal = goal.trim();
        while (true) {
            boolean newFactInferred = false;

            for (Rule rule : rules) {
                Map<String, String> substitutions = canInfer(rule, inferred);
                if (substitutions != null) {
                    // Apply the substitutions to the conclusion
                    String conclusion = applySubstitutions(rule.conclusion, substitutions);
                    if (!inferred.contains(conclusion)) {
                        inferred.add(conclusion);
                        newFactInferred = true;

                        if (Unify.unifyWithSubstitutions(conclusion, goal, new HashMap<>()) != null) {
                            return true;
                        }
                    }
                }
            }

            if (!newFactInferred) {
                break; // Δεν υπάρχουν νέα γεγονότα προς εξαγωγή
            }
        }

        return false; // Ο στόχος δεν αποδείχθηκε
    }

    // ΕΧΩ ΤΑ ΙΝΦΕΡΝΤ ΕΧΩ ΤΟΝ ΚΑΝΟΝΑ - ΕΦΑΡΜΟΖΕΤΑΙ Ο ΚΑΝΟΝΑΣ???
    // unify ΓΙΑ ΜΕΤΑΒΛΗΤΕΣ ΠΧ Χ=ΑΛΙ Υ=ΠΡΙΓΚΙΠΙΣΣΑ
    private Map<String, String> canInfer(Rule rule, Set<String> inferred) {
        Map<String, String> combinedSubstitutions = new HashMap<>();

        for (String premise : rule.premises) {
            // Apply current substitutions to the premise before unifying
            String substitutedPremise = applySubstitutions(premise, combinedSubstitutions);

            boolean satisfied = false;
            for (String fact : inferred) {
                Map<String, String> newSubs = Unify.unifyWithSubstitutions(fact, substitutedPremise, combinedSubstitutions);
                if (newSubs != null) {
                    combinedSubstitutions.putAll(newSubs);
                    satisfied = true;
                    break;
                }
            }

            if (!satisfied) {
                return null;
            }
        }

        return combinedSubstitutions;
    }

    // Εφαρμόζει τις υποκαταστάσεις σε ένα κατηγόρημα
    private String applySubstitutions(String predicate, Map<String, String> substitutions) {
        // Εξάγουμε το όνομα του κατηγορήματος και τα ορίσματά του
        String predicateName = predicate.substring(0, predicate.indexOf('('));
        String[] arguments = predicate.substring(predicate.indexOf('(') + 1, predicate.lastIndexOf(')')).split(",\\s*");

        // Αντικαθιστούμε τις μεταβλητές σύμφωνα με τον χάρτη substitutions
        for (int i = 0; i < arguments.length; i++) {
            String arg = arguments[i];
            if (substitutions.containsKey(arg)) {
                arguments[i] = substitutions.get(arg);
            }
        }

        // Ανακατασκευάζουμε το κατηγόρημα με τις υποκαταστάσεις
        StringBuilder result = new StringBuilder(predicateName + "(");
        for (int i = 0; i < arguments.length; i++) {
            result.append(arguments[i]);
            if (i < arguments.length - 1) {
                result.append(", ");
            }
        }
        result.append(")");
        return result.toString();
    }
}
