import java.util.*;

class Unify {

    public static Map<String, String> unify(String predicate1, String predicate2) {
        return unifyWithSubstitutions(predicate1, predicate2, new HashMap<>());
    }

    public static Map<String, String> unifyWithSubstitutions(String p1, String p2, Map<String, String> subs) {
        // Extract predicate names and arguments
        String name1 = p1.substring(0, p1.indexOf('(')).trim();
        String name2 = p2.substring(0, p2.indexOf('(')).trim();

        if (!name1.equals(name2)) {
            return null;
        }
        String[] args1 = p1.substring(p1.indexOf('(') + 1, p1.lastIndexOf(')')).split(",\\s*");
        String[] args2 = p2.substring(p2.indexOf('(') + 1, p2.lastIndexOf(')')).split(",\\s*");

        if (args1.length != args2.length) {
            return null;
        }

        Map<String, String> currentSubs = new HashMap<>(subs);
        for (int i = 0; i < args1.length; i++) {
            if (!unifyTerms(args1[i], args2[i], currentSubs)) {
                return null;
            }
        }
        return currentSubs;
    }

    private static boolean unifyTerms(String t1, String t2, Map<String, String> currentSubs) {
        t1 = applySubstitutionToTerm(t1, currentSubs);
        t2 = applySubstitutionToTerm(t2, currentSubs);

        // If they are now identical constants, unification is trivially true
        if (t1.equals(t2)) {
            return true;
        }
        // If t1 is a variable
        if (isVariable(t1)) {
            return unifyVariable(t1, t2, currentSubs);
        }
        // If t2 is a variable
        if (isVariable(t2)) {
            return unifyVariable(t2, t1, currentSubs);
        }
        // Different constants that are not equal cannot unify
        return false;
    }

    private static boolean unifyVariable(String var, String term, Map<String, String> currentSubs) {
        // If var is already substituted, unify that substitution with term
        if (currentSubs.containsKey(var)) {
            return unifyTerms(currentSubs.get(var), term, currentSubs);
        }
        // If term is a variable substituted to var, fine
        if (isVariable(term) && currentSubs.containsKey(term)) {
            return unifyTerms(var, currentSubs.get(term), currentSubs);
        }
        // Occur check: a variable should not unify with a structure containing itself
        if (occurCheck(var, term, currentSubs)) {
            return false;
        }

        currentSubs.put(var, term);
        return true;
    }

    private static boolean occurCheck(String var, String term, Map<String,String> currentSubs) {
        term = applySubstitutionToTerm(term, currentSubs);
        if (var.equals(term)) {
            return true;
        }
        
        return false;
    }

    private static String applySubstitutionToTerm(String term, Map<String,String> subs) {
        while (subs.containsKey(term) && !term.equals(subs.get(term))) {
            term = subs.get(term);
        }
        return term;
    }

    private static boolean isVariable(String s) {
        return Character.isLowerCase(s.charAt(0));
    }
}
