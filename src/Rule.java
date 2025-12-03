import java.util.List;

public class Rule {
    List<String> premises;
    String conclusion;

    Rule(List<String> premises, String conclusion) {
        this.premises = premises;
        this.conclusion = conclusion;
    }

    @Override
    public String toString() {
        return "\nRule: " + premises + " -> " + conclusion;
    }
}
