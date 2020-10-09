import common.Lexer;

import java.util.Map;

public class Start {
    public static void main(String[] args) {
        /*
        Map<String, Integer> tokens = Lexer.readFromFile("tokens.txt");
        System.out.println(tokens);
        */
        Lexer lexer = new Lexer();
        lexer.parseSourceFile("source2.c");

        System.out.println("Atoms: " + lexer.getAtoms());
        System.out.println("Identifiers: " + lexer.getIdentifiersTable());
        System.out.println("Constants: " + lexer.getConstantsTable());
    }
}
