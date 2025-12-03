# Logic Inference Engine (Forward Chaining)

A Java-based Artificial Intelligence project that implements **Forward Chaining** algorithms to solve inference problems. The project is split into two distinct modules handling different types of logic: **First-Order Logic (FOL)** and **Propositional Logic (PL)**.

## 📂 Project Structure

### 1. First-Order Logic Solver (`src/`)
This module implements a sophisticated inference engine capable of handling variables, predicates, and unification.

* **Algorithm**: Generalized Forward Chaining with Unification.
* **Key Class**: `FC.java` implements the inference loop, checking rules against known facts and inferring new conclusions.
* **Unification**: `Unify.java` handles the matching of logic terms, supporting variable substitution (e.g., matching `Knows(x, y)` with `Knows(John, Mary)`).
* **Syntax**:
    * **Variables**: Must start with a lowercase letter (e.g., `x`, `y`).
    * **Constants**: Start with an uppercase letter (e.g., `John`, `Alice`).
    * **Rules**: Defined using `∧` for AND and `->` for implication.
    * **Example Rule**: `Knows(x,y)∧Knows(y,z)->Knows(x,z)`.

### 2. Propositional Logic Solver (`src2/`)
This module implements a standard, efficient Forward Chaining algorithm for definite clauses (Horn Clauses).

* **Algorithm**: Agenda-based Forward Chaining. It tracks the number of unsatisfied premises ("count") for each rule to determine when it triggers.
* **Key Class**: `PL_FC_Entails.java`.
* **Syntax**:
    * **Symbols**: Atomic strings (e.g., `A`, `B`, `rain`).
    * **Rules**: Premises are separated by spaces (implicit AND).
    * **Example Rule**: `B D -> E` (If B AND D are true, then E is true).

## 🚀 How to Run

### Running the FOL Solver (src)
1.  Navigate to the source folder:
    ```bash
    cd src
    ```
2.  Compile the Java files:
    ```bash
    javac *.java
    ```
3.  Run the main application:
    ```bash
    java FOL_FC_main
    ```
4.  **Input**: When prompted, provide the path to your knowledge base file (e.g., `KB.txt`) and the sentence you wish to prove (e.g., `Knows(John,Alice)`).

### Running the PL Solver (src2)
1.  Navigate to the source folder:
    ```bash
    cd src2
    ```
2.  Compile the Java files:
    ```bash
    javac *.java
    ```
3.  Run the main application:
    ```bash
    java Main
    ```
4.  **Input**: Provide the path to the knowledge base (e.g., `knowledge_base.txt`) and the query symbol to verify.

## 📄 Knowledge Base Examples

**FOL (`src/KB.txt`)**:
```text
Knows(John,Mary)
Knows(Mary,Alice)
Knows(x,y)∧Knows(y,z)->Knows(x,z)
