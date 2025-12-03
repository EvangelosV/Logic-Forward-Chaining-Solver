import java.util.*;

class Rule {
    List<String> premises; 
    String conclusion;      

    public Rule(List<String> premises, String conclusion) {
        this.premises = premises;
        this.conclusion = conclusion;
    }

    @Override
    public String toString() {
        return String.join(" ", premises) + " -> " + conclusion;
    }
}